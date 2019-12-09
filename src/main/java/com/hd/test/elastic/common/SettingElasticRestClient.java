package com.hd.test.elastic.common;

import com.hd.test.common.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * ES操作client类
 *
 * @author Sands
 */
public class SettingElasticRestClient {
    private static final String DEFAULT_TYPE = "default";
    private ElasticIndexSetting setting;
    private ElasticRestClient client;

    public SettingElasticRestClient(ElasticRestClient client, ElasticIndexSetting setting) {
        this.client = client;
        this.setting = setting;
    }

    public ElasticRestClient getClient() {
        return client;
    }

    public ElasticIndexSetting getSetting() {
        return setting;
    }

    public String getIndex() {
        return getIndex(new Date());
    }

    public String getIndex(Date date) {
        date = date == null ? new Date() : date;

        String index = setting.getIndex();
        if (setting.isUseDateDay()) {
            return ElasticRestUtils.getIndexNameByDay(index, date);
        } else if (setting.isUseDateMonth()) {
            return ElasticRestUtils.getIndexNameByMonth(index, date);
        } else {
            return index;
        }
    }

    public String getType() {
        return StringUtils.isNotEmpty(setting.getType()) ? setting.getType() : DEFAULT_TYPE;
    }

    /**
     * 创建索引
     */
    public boolean createIndex() {
        String index = getIndex();
        return client.createIndex(index);
    }

    /**
     * 索引是否存在
     */
    public boolean existsIndex() {
        String index = getIndex();
        return client.existsIndex(index);
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex() {
        String index = getIndex();
        return client.deleteIndex(index);
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(Date date) {
        String index = getIndex(date);
        return client.deleteIndex(index);
    }

    /**
     * 字段类型映射
     */
    public boolean mappingIndex(Class<?> mappingClazz) {
        String index = getIndex();
        String type = getType();

        return client.mappingIndex(index, type, mappingClazz);
    }

    /**
     * 字段类型映射
     */
    public boolean mappingIndex(XContentBuilder mapping) {
        String index = getIndex();
        String type = getType();

        return client.mappingIndex(index, type, mapping);
    }

    /**
     * 创建索引并且进行字段类型映射
     */
    public boolean createAndMappingIndex(Class<?> mappingClazz, boolean mustMapping) {
        String index = getIndex();
        String type = getType();

        return client.createAndMappingIndex(index, type, mappingClazz, mustMapping);
    }

    /**
     * 创建索引并且进行字段类型映射
     */
    public boolean createAndMappingIndex(XContentBuilder mapping, boolean mustMapping) {
        String index = getIndex();
        String type = getType();

        return client.createAndMappingIndex(index, type, mapping, mustMapping);
    }

    /**
     * 该字段是否存在
     */
    public boolean existsField(String fieldName) {
        String index = getIndex();
        String type = getType();

        return client.existsField(index, type, fieldName);
    }

    /**
     * 设置数据
     */
    public boolean set(Object id, Map<String, Object> data) {
        String index = getIndex();
        String type = getType();

        return client.set(index, type, id, data);
    }

    /**
     * 设置model数据
     */
    public boolean setModel(Object data) {
        String index = getIndex();
        String type = getType();

        return client.setModel(index, type, data);
    }

    /**
     * 设置model数据
     */
    public boolean setModel(Object id, Object data) {
        String index = getIndex();
        String type = getType();

        return client.setModel(index, type, id, data);
    }

    /**
     * 批量设置
     */
    public <T> boolean bulkSet(List<T> list) {
        String index = getIndex();
        String type = getType();

        return client.bulkSet(index, type, list);
    }

    /**
     * 更新根据id
     */
    public boolean update(String id, Map<String, Object> updateData) {
        String index = getIndex();
        String type = getType();

        return client.update(index, type, id, updateData);
    }

    /**
     * 更新根据id
     */
    public boolean update(String id, Map<String, Object> updateData,
                          Function<Map.Entry<String, Object>, String> getUpdateLine) {
        String index = getIndex();
        String type = getType();

        return client.update(index, type, id, updateData, getUpdateLine);
    }

    /**
     * 更新根据查询条件
     */
    public boolean updateByQuery(QueryBuilder query, Map<String, Object> updateData) {
        String index = getIndex();
        String type = getType();

        return client.updateByQuery(index, type, query, updateData);
    }

    /**
     * 更新根据查询条件
     */
    public boolean updateByQuery(QueryBuilder query, Map<String, Object> updateData,
                                 Function<Map.Entry<String, Object>, String> getUpdateLine) {
        String index = getIndex();
        String type = getType();

        return client.updateByQuery(index, type, query, updateData, getUpdateLine);
    }

    /**
     * 删除
     */
    public boolean delete(String id) {
        String index = getIndex();
        String type = getType();

        return client.delete(index, type, id);
    }

    /**
     * 删除根据查询条件
     */
    public boolean deleteByQuery(QueryBuilder query) {
        String index = getIndex();
        String type = getType();

        return client.deleteByQuery(index, type, query);
    }

    /**
     * 获取
     */
    public <T> T get(String id, Class<T> clazz) {
        String index = getIndex();
        String type = getType();

        return client.get(index, type, id, clazz);
    }

    /**
     * 分页获取
     */
    public <T> ElasticPageInfo<T> findPage(QueryBuilder query,
                                           String orderBy, SortOrder orderType, int pageIndex, int pageSize,
                                           Class<T> clazz) {
        String index = getIndex();
        String type = getType();

        return client.findPage(index, type, query, orderBy, orderType, pageIndex, pageSize, clazz);
    }

    /**
     * 统计
     */
    public long count(QueryBuilder query,
                      String orderBy, SortOrder orderType) {
        String index = getIndex();
        String type = getType();

        return client.count(index, type, query, orderBy, orderType);
    }


    /**
     * 设置model数据
     */
    public boolean setModelByIndex(Date indexDate, Object data) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.setModel(index, type, data);
    }

    /**
     * 批量设置
     */
    public <T> boolean bulkSetByIndex(Date indexDate, List<T> list) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.bulkSet(index, type, list);
    }

    /**
     * 更新根据id
     */
    public boolean updateByIndex(Date indexDate, String id, Map<String, Object> updateData) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.update(index, type, id, updateData);
    }

    /**
     * 删除
     */
    public boolean deleteByIndex(Date indexDate, String id) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.delete(index, type, id);
    }

    /**
     * 获取
     */
    public <T> T getByIndex(Date indexDate, String id, Class<T> clazz) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.get(index, type, id, clazz);
    }

    /**
     * 分页获取
     */
    public <T> ElasticPageInfo<T> findPageByIndex(Date indexDate, QueryBuilder query,
                                                  String orderBy, SortOrder orderType, int pageIndex, int pageSize,
                                                  Class<T> clazz) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.findPage(index, type, query, orderBy, orderType, pageIndex, pageSize, clazz);
    }

    /**
     * 统计
     */
    public long countByIndex(Date indexDate, QueryBuilder query,
                             String orderBy, SortOrder orderType) {
        String index = getIndex(indexDate);
        String type = getType();

        return client.count(index, type, query, orderBy, orderType);
    }


}
