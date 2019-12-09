package com.hd.test.elastic.manager;

import com.hd.test.common.*;
import com.hd.test.elastic.common.ElasticIndexSetting;
import com.hd.test.elastic.common.ElasticRestClient;
import com.hd.test.elastic.common.SettingElasticRestClient;
import com.hd.test.elastic.config.ElasticProperties;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ElasticManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticManager.class);

    private final ElasticProperties elasticProperties;
    private final Thread checkQueueTask;
    /**
     * 创建索引的事件
     */
    private final Map<Class<?>, SettingElasticRestClient> createIndexFuncs = new ConcurrentHashMap<>();
    private JestClient jestClient;
    private ElasticRestClient client;

    public ElasticManager(ElasticProperties elasticProperties) {
        this.elasticProperties = elasticProperties;

        init();

        checkQueueTask = ThreadUtils.getThread(true, new CheckTask(elasticProperties.getCheckCreateIndexSpan()));
        checkQueueTask.start();
    }

    private void init() {
        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig.Builder builder = new HttpClientConfig
                .Builder(CollectionUtils.toList(elasticProperties.getServerUris()))
                .multiThreaded(elasticProperties.isMultiThreaded())
                //Per default this implementation will create no more than 2 concurrent connections per given route
                .defaultMaxTotalConnectionPerRoute(elasticProperties.getDefaultMaxTotalConnectionPerRoute())
                // and no more 20 connections in total
                .maxTotalConnection(elasticProperties.getMaxTotalConnection());

        if (StringUtils.isNotEmpty(elasticProperties.getUsername())) {
            builder.defaultCredentials(elasticProperties.getUsername(), elasticProperties.getPassword());
        }

        factory.setHttpClientConfig(builder.build());

        jestClient = factory.getObject();
        client = new ElasticRestClient(jestClient);
    }

    public SettingElasticRestClient bindCheckCreateIndex(Class<?> clazz, ElasticIndexSetting setting) {
        SettingElasticRestClient settingClient = new SettingElasticRestClient(client, setting);
        //先调用创建一下索引
        Date date = new Date();
        createIndex(settingClient, date, true);
        Date nextMonth = DateUtils.nextMonth(date);
        createIndex(settingClient, nextMonth, true);

        //然后放到队列中定期进行检测
        createIndexFuncs.put(clazz, settingClient);

        return settingClient;
    }

    private void check() {
        if (CollectionUtils.isNotEmpty(createIndexFuncs)) {
            for (Map.Entry<Class<?>, SettingElasticRestClient> entry : createIndexFuncs.entrySet()) {
                SettingElasticRestClient settingClient = entry.getValue();
                checkCreateIndex(settingClient); //检测创建索引
                checkDeleteIndex(settingClient); //检测删除索引
            }
        }
    }

    /**
     * 检测创建索引
     */
    private void checkCreateIndex(SettingElasticRestClient settingClient) {

        //创建第一个索引（如果按照时间为准创建索引的话，例如创建本月的索引）
        Date date = new Date();
        String index = settingClient.getIndex(date);
        String type = settingClient.getType();
        boolean suc = createIndex(settingClient, date, false);
        if (suc) {
            LOGGER.info("定期检测创建ES索引成功, index: " + index + ", type: " + type);
        } else {
            LOGGER.error("定期检测创建ES索引失败, index: " + index + ", type: " + type);
        }

        //创建下一个索引（如果按照时间为准创建索引的话，例如创建下个月的索引）
        //为什么提前创建好下一个索引，目的避免在长时间运行的程序，刚好进到下一个月后，索引还没有初始化创建，而导致出错了
        Date nextMonth = DateUtils.nextMonth(date);
        String nextIndex = settingClient.getIndex(nextMonth);
        suc = createIndex(settingClient, nextMonth, false);
        if (suc) {
            LOGGER.info("定期检测创建ES索引成功 next index: " + nextIndex + ", type: " + type);
        } else {
            LOGGER.error("定期检测创建ES索引失败 next index: " + nextIndex + ", type: " + type);
        }
    }

    /**
     * 检测创建索引
     */
    private boolean createIndex(SettingElasticRestClient settingClient, Date date, boolean mustMapping) {
        ElasticIndexSetting setting = settingClient.getSetting();

        String index = settingClient.getIndex(date);
        String type = settingClient.getType();
        return client.createAndMappingIndex(index, type, setting.getMappingClass(), mustMapping);
    }

    /**
     * 检测删除索引
     */
    private void checkDeleteIndex(SettingElasticRestClient settingClient) {
        checkDeleteLastIndex(settingClient);
        checkDeleteLast2Index(settingClient);
    }

    /**
     * 检测删除上个月的索引
     */
    private void checkDeleteLastIndex(SettingElasticRestClient settingClient) {
        Date date = new Date();
        if (DateUtils.getDay(date) >= elasticProperties.getLastMonthDeleteCheckSpan()) {   //如果当月已经超过20号了，那么删除上个月的
            //上个月的索引，删除
            Date lastMonth = DateUtils.month(DateUtils.addMonths(date, -1));
            String lastMonthIndex = settingClient.getIndex(lastMonth);

            //测试创建，然后下面删除
//        client.createAndMappingIndex(lastMonthIndex, settingClient.getType(), settingClient.getSetting().getMappingClass(), false);

            if (client.existsIndex(lastMonthIndex)) {
                boolean suc = client.deleteIndex(lastMonthIndex);
                if (suc) {
                    LOGGER.info("定期检测删除ES索引成功, index: " + lastMonthIndex);
                } else {
                    LOGGER.info("定期检测删除ES索引失败, index: " + lastMonthIndex);
                }
            }
        }
    }

    /**
     * 检测删除上上个月的索引
     */
    private void checkDeleteLast2Index(SettingElasticRestClient settingClient) {
        Date date = new Date();
        //上上个月的索引，删除
        Date last2Month = DateUtils.month(DateUtils.addMonths(date, -2));
        String last2MonthIndex = settingClient.getIndex(last2Month);

        //测试创建，然后下面删除
//        client.createAndMappingIndex(last2MonthIndex, settingClient.getType(), settingClient.getSetting().getMappingClass(), false);

        if (client.existsIndex(last2MonthIndex)) {
            boolean suc = client.deleteIndex(last2MonthIndex);
            if (suc) {
                LOGGER.info("定期检测删除ES索引成功, index: " + last2MonthIndex);
            } else {
                LOGGER.info("定期检测删除ES索引失败, index: " + last2MonthIndex);
            }
        }
    }

    /**
     * 检测线程
     */
    private class CheckTask extends CyclicTask {

        public CheckTask(long tSleep) {
            super(tSleep * 1000, 10000);
        }

        @Override
        protected void runActualTask() {
            check();
        }
    }

}
