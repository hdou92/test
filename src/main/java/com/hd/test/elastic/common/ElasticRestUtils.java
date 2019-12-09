package com.hd.test.elastic.common;

import com.hd.test.common.DateUtils;
import com.hd.test.common.JsonUtils;
import com.hd.test.common.StringUtils;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * elastic rest帮助类
 *
 * @author Sands
 */
public class ElasticRestUtils {
    private static final String INDEX_NAME_SPLIT = "-";

    /**
     * 使用脚本进行修改
     */
    public static String buildUpdateData(Map<String, Object> updateData) throws IOException {
        //bad
//        StringBuilder sb = new StringBuilder();
//        for (Map.Entry<String, Object> pair : updateData.entrySet()) {
//            sb.append("ctx._source." + pair.getKey() + " = params." + pair.getKey() + ";");
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("script", sb.toString());
//        map.put("params", updateData);
//        return ZJsonUtils.toJsonEx(map);


        //good
//        StringBuilder script = new StringBuilder();
//        for (Map.Entry<String, Object> pair : updateData.entrySet()) {
//            script.append("ctx._source." + pair.getKey() + " = " + ZJsonUtils.toJson(pair.getValue()) + ";");
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("script", script.toString());
//        return ZJsonUtils.toJsonEx(map);


        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> pair : updateData.entrySet()) {
            sb.append("ctx._source." + pair.getKey() + " = params." + pair.getKey() + ";");
        }
        return buildUpdateString(updateData, sb);
    }

    /**
     * 使用脚本进行修改
     */
    public static String buildUpdateData(Map<String, Object> updateData, Function<Map.Entry<String, Object>, String> getUpdateLine) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> pair : updateData.entrySet()) {
//            sb.append("ctx._source." + pair.getKey() + " = params." + pair.getKey() + ";");
            sb.append(getUpdateLine.apply(pair));
        }
        return buildUpdateString(updateData, sb);
    }

    private static String buildUpdateString(Map<String, Object> updateData, StringBuilder sb) throws IOException {
        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, sb.toString(), updateData);
        String data = Strings.toString(script.toXContent(jsonBuilder(), null));

        Map<String, Object> map = new HashMap<>();
        map.put("script", JsonUtils.fromJsonEx(data));
        return JsonUtils.toJsonEx(map);
    }

    /**
     * 使用脚本进行修改
     */
    public static String buildUpdateDataByQuery(Map<String, Object> updateData,
                                                QueryBuilder query) throws IOException {
        StringBuilder script = new StringBuilder();
        for (Map.Entry<String, Object> pair : updateData.entrySet()) {
            script.append("ctx._source." + pair.getKey() + " = " + JsonUtils.toJson(pair.getValue()) + ";");
        }

        return buildUpdateDataByQueryString(script, query);
    }

    /**
     * 使用脚本进行修改
     */
    public static String buildUpdateDataByQuery(Map<String, Object> updateData,
                                                QueryBuilder query,
                                                Function<Map.Entry<String, Object>, String> getUpdateLine) throws IOException {
        StringBuilder script = new StringBuilder();
        for (Map.Entry<String, Object> pair : updateData.entrySet()) {
//            script.append("ctx._source." + pair.getKey() + " = " + ZJsonUtils.toJson(pair.getValue()) + ";");
            script.append(getUpdateLine.apply(pair));
        }

        return buildUpdateDataByQueryString(script, query);
    }

    private static String buildUpdateDataByQueryString(StringBuilder script, QueryBuilder query) throws IOException {
        final XContentBuilder xContentBuilder = jsonBuilder()
                .startObject()
                .field("query", query)
                .startObject("script")
                .field("inline", script.toString())
                .endObject()
                .endObject();

        return buildXContentBuilder(xContentBuilder);
    }

    public static String buildIndexMapping(String type, XContentBuilder mapping) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put(type, JsonUtils.fromJsonEx(buildXContentBuilder(mapping)));
        return JsonUtils.toJsonEx(map);
    }

    public static String buildXContentBuilder(XContentBuilder xContentBuilder) throws IOException {
        xContentBuilder.flush();
        return ((ByteArrayOutputStream) xContentBuilder.getOutputStream()).toString("UTF-8");
    }

    /**
     * 分页查询，返回SearchSourceBuilder
     *
     * @param queryBuilder 查询条件
     * @param orderBy      排序字段
     * @param orderType    排序方式
     * @param pageIndex    当前页码(从1开始)
     * @param pageSize     分页大小
     * @return 返回SearchSourceBuilder
     */
    public static SearchSourceBuilder getPageBuilder(QueryBuilder queryBuilder, String orderBy,
                                                     SortOrder orderType, Integer pageIndex,
                                                     Integer pageSize, boolean pageSizeMustValid) {
        // 创建查询索引
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 设置查询条件
        builder.query(queryBuilder);

        // 分页
        if (pageIndex <= 0) {
            pageIndex = 1;
        }

        if (pageSizeMustValid) {
            if (pageSize <= 0) {
                pageSize = 20;
            }
        } else {
            if (pageSize < 0) {
                //为0的时候用于统计总数的，因此不能 <= 0时进行设置
                //            if (pageSize <= 0) {
                pageSize = 20;
            }
        }

        // 设置查询数据的位置,分页用吧
        builder.from(pageSize * (pageIndex - 1));
        // 设置查询结果集的最大条数
        builder.size(pageSize);
        //设置排序
        builder.sort(orderBy, orderType);

        return builder;
    }

    /**
     * 获取索引名称根据年月
     */
    public static String getIndexNameByMonth(String index, Date date) {
        return StringUtils.formatHumpToUnderline(index) + INDEX_NAME_SPLIT + DateUtils.format(date, "yyyy.MM");
    }

    /**
     * 获取索引名称根据年月日
     */
    public static String getIndexNameByDay(String index, Date date) {
        //logstash记录index的格式：logstash-2015.01.01
        return StringUtils.formatHumpToUnderline(index) + INDEX_NAME_SPLIT + DateUtils.format(date, "yyyy.MM.dd");
    }
}
