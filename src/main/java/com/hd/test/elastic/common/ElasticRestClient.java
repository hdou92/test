package com.hd.test.elastic.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hd.test.common.CollectionUtils;
import com.hd.test.common.StringUtils;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.fields.FieldCapabilities;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * ES操作client类
 *
 * @author Sands
 */
public class ElasticRestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticRestClient.class);
    private final JestClient client;

    public ElasticRestClient(JestClient client) {
        this.client = client;
    }

    public JestClient getClient() {
        return client;
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String index) {
        try {
            return isSucceeded(client.execute(new CreateIndex.Builder(index).build()));
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 索引是否存在
     */
    public boolean existsIndex(String index) {
        try {
            IndicesExists build = new IndicesExists.Builder(index).build();
            JestResult result = client.execute(build);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String index) {
        try {
            DeleteIndex get = new DeleteIndex.Builder(index).build();
            return isSucceeded(client.execute(get));
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 字段类型映射
     */
    public boolean mappingIndex(String index, String type, Class<?> mappingClazz) {
        try {
            return mappingIndex(index, type, ElasticUtils.toXContentBuilder(mappingClazz));
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 字段类型映射
     */
    public boolean mappingIndex(String index, String type, XContentBuilder mapping) {
        try {
            String data = ElasticRestUtils.buildIndexMapping(type, mapping);
            PutMapping putMapping = new PutMapping.Builder(index, type, data).build();
            boolean suc = isSucceeded(client.execute(putMapping));
            if (suc) {
                LOGGER.info("mappingIndex, index: " + index + ", type: " + type + ", mapping: " + data);
            }
            return suc;
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 创建索引并且进行字段类型映射
     */
    public boolean createAndMappingIndex(String index, String type, Class<?> mappingClazz, boolean mustMapping) {
        try {
            return createAndMappingIndex(index, type, ElasticUtils.toXContentBuilder(mappingClazz), mustMapping);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 创建索引并且进行字段类型映射
     */
    public boolean createAndMappingIndex(String index, String type, XContentBuilder mapping, boolean mustMapping) {
        boolean exists = existsIndex(index);
        boolean newCreate = false;
        if (!exists) {
            newCreate = createIndex(index);
        }

        if (newCreate) {
            //新创建的，那么必须进行mapping
            mappingIndex(index, type, mapping);
        } else if (exists) {
            //如果已经存在了，那么判断是否必须进行mapping
            if (mustMapping) {
                mappingIndex(index, type, mapping);
            }
        }

        return exists || newCreate;
    }

    /**
     * 该字段是否存在
     */
    public boolean existsField(String index, String type, String fieldName) {
        try {
            FieldCapabilities build = new FieldCapabilities.Builder(fieldName)
                    .setIndex(index).build();
            JestResult result = client.execute(build);
            JsonObject jsonObject = result.getJsonObject();
            //不存在时：{"fields":{}}
            //存在时：{"fields":{"message":{"text":{"type":"text","searchable":true,"aggregatable":false}}}}
            JsonObject fields = jsonObject.getAsJsonObject("fields");
            if (fields != null) {
                JsonElement field = fields.get(fieldName);
                if (field != null) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 设置数据
     */
    public boolean set(String index, String type, Object id, Map<String, Object> data) {
        try {
            Index idx = new Index.Builder(data)
                    .id(id.toString()) //需要手动设置id
                    .index(index).type(type).build();
            DocumentResult result = client.execute(idx);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 设置model数据
     */
    public boolean setModel(String index, String type, Object data) {
        try {

            Index idx = new Index.Builder(data)
//                    .id(id.toString())   //如果在model中设置了@JestId注解字段，那么默认会自动设置id
                    .index(index).type(type).build();
            DocumentResult result = client.execute(idx);
            return isSucceeded(result);

        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 设置model数据
     */
    public boolean setModel(String index, String type, Object id, Object data) {
        try {

            Index idx = new Index.Builder(data)
                    .id(id.toString())   //如果在model中设置了@JestId注解字段，那么默认会自动设置id
                    .index(index).type(type).build();
            DocumentResult result = client.execute(idx);
            return isSucceeded(result);

        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 批量设置
     */
    public <T> boolean bulkSet(String index, String type, List<T> list) {
        try {

            Bulk.Builder builder = new Bulk.Builder()
                    .defaultIndex(index)
                    .defaultType(type);

            for (T data : list) {
                builder.addAction(new Index.Builder(data).build());

                //除了set，delete等的操作也是可以的
//            builder.addAction(new Delete.Builder("1").index("twitter").type("tweet").build());
            }

            Bulk bulk = builder.build();
            BulkResult result = client.execute(bulk);
            return isSucceeded(result);

        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 更新根据id
     */
    public boolean update(String index, String type, String id, Map<String, Object> updateData) {
        try {
            String data = ElasticRestUtils.buildUpdateData(updateData);
            Update build = new Update.Builder(data).index(index).type(type).id(id).build();
            DocumentResult result = client.execute(build);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 更新根据id
     */
    public boolean update(String index, String type, String id, Map<String, Object> updateData,
                          Function<Map.Entry<String, Object>, String> getUpdateLine) {
        try {
            String data = ElasticRestUtils.buildUpdateData(updateData, getUpdateLine);
            Update build = new Update.Builder(data).index(index).type(type).id(id).build();
            DocumentResult result = client.execute(build);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 更新根据查询条件
     */
    public boolean updateByQuery(String index, String type, QueryBuilder query, Map<String, Object> updateData) {
        try {
            String data = ElasticRestUtils.buildUpdateDataByQuery(updateData, query);
            UpdateByQuery get = new UpdateByQuery.Builder(data)
                    .addIndex(index)
                    .addType(type)
                    .build();
            UpdateByQueryResult result = client.execute(get);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 更新根据查询条件
     */
    public boolean updateByQuery(String index, String type, QueryBuilder query, Map<String, Object> updateData,
                                 Function<Map.Entry<String, Object>, String> getUpdateLine) {
        try {
            String data = ElasticRestUtils.buildUpdateDataByQuery(updateData, query, getUpdateLine);
            UpdateByQuery get = new UpdateByQuery.Builder(data)
                    .addIndex(index)
                    .addType(type)
                    .build();
            UpdateByQueryResult result = client.execute(get);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 删除
     */
    public boolean delete(String index, String type, String id) {
        try {
            Delete get = new Delete.Builder(id).index(index).type(type).build();
            DocumentResult result = client.execute(get);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 删除根据查询条件
     */
    public boolean deleteByQuery(String index, String type, QueryBuilder query) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            DeleteByQuery doDelete = new DeleteByQuery.Builder(searchSourceBuilder.toString())
                    .addIndex(index)
                    .addType(type)
                    .build();
            JestResult result = client.execute(doDelete);
            return isSucceeded(result);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 获取
     */
    public <T> T get(String index, String type, String id, Class<T> clazz) {
        try {

            Get get = new Get.Builder(index, id).type(type).build();
            DocumentResult result = client.execute(get);
            if (isSucceeded(result)) {
                return result.getSourceAsObject(clazz);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 分页获取
     */
    public <T> ElasticPageInfo<T> findPage(String index, String type, QueryBuilder query,
                                           String orderBy, SortOrder orderType, int pageIndex, int pageSize,
                                           Class<T> clazz) {
        try {
            SearchSourceBuilder searchSourceBuilder = ElasticRestUtils.getPageBuilder(query, orderBy, orderType, pageIndex, pageSize, true);
            Search search = new Search.Builder(searchSourceBuilder.toString())
                    // multiple index or types can be added.
                    .addIndex(index)
                    .addType(type)
                    .build();
            SearchResult result = client.execute(search);
            Long total = 0L;
            List<T> list = new ArrayList<>();
            if (isSucceeded(result)) {
                total = result.getTotal();
                List<SearchResult.Hit<T, Void>> hits = result.getHits(clazz);
                if (CollectionUtils.isNotEmpty(hits)) {
                    for (SearchResult.Hit<T, Void> hit : hits) {
                        list.add(hit.source);
                    }
                }
            }
            return new ElasticPageInfo<>(pageIndex, pageSize, total, list);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    /**
     * 统计
     */
    public long count(String index, String type, QueryBuilder query,
                      String orderBy, SortOrder orderType) {
        try {
            SearchSourceBuilder searchSourceBuilder = ElasticRestUtils.getPageBuilder(query, orderBy, orderType, 1, 0, false);
            Search search = new Search.Builder(searchSourceBuilder.toString())
                    // multiple index or types can be added.
                    .addIndex(index)
                    .addType(type)
                    .build();
            SearchResult result = client.execute(search);
            if (isSucceeded(result)) {
                return result.getTotal();
            }
            return 0;
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    private boolean isSucceeded(JestResult result) {
        if (result != null && result.isSucceeded()) {
            return true;
        }
        return false;
    }

}
