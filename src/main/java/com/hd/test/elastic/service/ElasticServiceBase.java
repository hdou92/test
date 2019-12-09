package com.hd.test.elastic.service;

import com.hd.test.common.DateUtils;
import com.hd.test.common.StringUtils;
import com.hd.test.elastic.common.ElasticIndexSetting;
import com.hd.test.elastic.common.ElasticPageInfo;
import com.hd.test.elastic.common.SettingElasticRestClient;
import com.hd.test.elastic.manager.ElasticManager;
import com.hd.test.elastic.modules.device.entity.ElasticModel;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * elastic的service类
 *
 * @param <T> entity
 * @author Sands
 */
public abstract class ElasticServiceBase<T extends ElasticModel> {
    protected final SettingElasticRestClient client;
    private Class<T> mappingClass;

    public ElasticServiceBase(String name, Class<T> mappingClass, ElasticManager elasticManager) {
        this.mappingClass = mappingClass;
        this.client = elasticManager.bindCheckCreateIndex(this.getClass(),
                new ElasticIndexSetting.Builder(StringUtils.formatHumpToUnderline(name), mappingClass, true).build());
    }

    public String getIndex(Date date) {
        return date == null ? client.getIndex() : client.getIndex(date);
    }

    public SettingElasticRestClient getClient() {
        return client;
    }

    public boolean add(T model) {
        return client.setModel(model);
    }

    public boolean addAll(List<T> list) {
        return client.bulkSet(list);
    }

    public boolean update(String id, Map<String, Object> updateData) {
        return client.update(id, updateData);
    }

    public boolean delete(String id) {
        return client.delete(id);
    }

    public T get(String id) {
        return client.get(id, mappingClass);
    }

    public ElasticPageInfo<T> findPage(QueryBuilder query,
                                       String orderBy, SortOrder orderType, int pageIndex, int pageSize) {
        return client.findPage(query, orderBy, orderType, pageIndex, pageSize, mappingClass);
    }

    public long count(QueryBuilder query,
                      String orderBy, SortOrder orderType) {
        return client.count(query, orderBy, orderType);
    }


    public boolean addByIndex(Date indexDate, T model) {
        return client.setModelByIndex(indexDate, model);
    }

    public boolean addAllByIndex(Date indexDate, List<T> list) {
        return client.bulkSetByIndex(indexDate, list);
    }

    public boolean updateByIndex(Date indexDate, String id, Map<String, Object> updateData) {
        return client.updateByIndex(indexDate, id, updateData);
    }

    public boolean deleteByIndex(Date indexDate, String id) {
        return client.deleteByIndex(indexDate, id);
    }

    public T getByIndex(Date indexDate, String id) {
        return client.getByIndex(indexDate, id, mappingClass);
    }

    public ElasticPageInfo<T> findPageByIndex(Date indexDate, QueryBuilder query,
                                              String orderBy, SortOrder orderType, int pageIndex, int pageSize) {
        return client.findPageByIndex(indexDate, query, orderBy, orderType, pageIndex, pageSize, mappingClass);
    }

    public long countByIndex(Date indexDate, QueryBuilder query,
                             String orderBy, SortOrder orderType) {
        return client.countByIndex(indexDate, query, orderBy, orderType);
    }

    /**
     * 获取根据指定的id，如果没有获取到，那么获取上个月的
     */
    public T getOrLastMonth(String id) {
        T model = get(id);
        if (model == null) {
            //如果为null，那么获取上个月的
            model = getByIndex(DateUtils.addMonths(new Date(), -1), id);
        }
        return model;
    }

    /**
     * 获取模糊匹配的字符串值
     */
    protected String getQueryLikeString(String val) {
        return "*" + val + "*";
    }

    /**
     * 时间范围查询条件
     */
    protected RangeQueryBuilder getDateRangeQuery(String fieldName, Date beginDate, Date endDate,
                                                  boolean ifNullMustToday) {
        if (beginDate == null) {
            if (endDate == null) {
                beginDate = DateUtils.today();
            } else {
                beginDate = DateUtils.today(endDate);
            }
        }

        RangeQueryBuilder timeQuery = QueryBuilders.rangeQuery(fieldName).gte(beginDate);
        if (endDate != null) {
            if (DateUtils.toString(endDate).equals(DateUtils.toString(beginDate))) {
                //如果结束时间与开始时间的时间一样，那么延续endDate为beginDate下一天的0点时间
                endDate = DateUtils.addDays(DateUtils.today(beginDate), 1);
            }
            timeQuery.lt(endDate);
        } else if (ifNullMustToday) {
//            timeQuery.lt(new Date());
            timeQuery.lt(DateUtils.addDays(beginDate, 1));
        }
        return timeQuery;
    }

    /**
     * 时间范围查询条件
     */
    protected RangeQueryBuilder getTodayQuery(String fieldName, Date date) {
        Date beginDate = null;
        Date endDate = date;
        if (date == null) {
            beginDate = DateUtils.today();
            endDate = DateUtils.addDays(beginDate, 1);
        } else {
            beginDate = DateUtils.today(date);
            if (DateUtils.toString(endDate).equals(DateUtils.toString(beginDate))) {
                //如果结束时间与开始时间的时间一样，那么延续endDate为beginDate下一天的0点时间
                endDate = DateUtils.addDays(beginDate, 1);
            }
        }
//        Date endDate = ZDateUtils.addDays(beginDate, 1);

        return QueryBuilders.rangeQuery(fieldName).gte(beginDate).lt(endDate);
    }

}
