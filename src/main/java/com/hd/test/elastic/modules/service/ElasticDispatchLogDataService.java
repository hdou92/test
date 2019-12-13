package com.hd.test.elastic.modules.service;

import com.hd.test.common.DateUtils;
import com.hd.test.common.StringUtils;
import com.hd.test.elastic.common.ElasticPageInfo;
import com.hd.test.elastic.config.ElasticIndexConsts;
import com.hd.test.elastic.manager.ElasticManager;
import com.hd.test.elastic.modules.entity.ElasticDispatchLogData;
import com.hd.test.elastic.modules.model.ElasticDispatchLogDataPageQuery;
import com.hd.test.elastic.service.ElasticServiceBase;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

@Service
public class ElasticDispatchLogDataService extends ElasticServiceBase<ElasticDispatchLogData> {
    public ElasticDispatchLogDataService(ElasticManager elasticManager) {
        super(ElasticIndexConsts.DISPATCH_LOG_DATA_INDEX_NAME, ElasticDispatchLogData.class, elasticManager);
    }

    /**
     * 分页查询
     */
    public ElasticPageInfo<ElasticDispatchLogData> findPage(ElasticDispatchLogDataPageQuery queryInfo) {
        BoolQueryBuilder query = getQuery(queryInfo);

        return client.findPageByIndex(queryInfo.getBeginDate(), query, "timestamp", SortOrder.DESC,
                queryInfo.getPageIndex(), queryInfo.getPageSize(),
                ElasticDispatchLogData.class);
    }

    /**
     * 统计
     */
    public long countBy(ElasticDispatchLogDataPageQuery queryInfo) {
        BoolQueryBuilder query = getQuery(queryInfo);

        return client.countByIndex(queryInfo.getBeginDate(), query, "timestamp", SortOrder.DESC);
    }

    private BoolQueryBuilder getQuery(ElasticDispatchLogDataPageQuery queryInfo) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();

//        Date beginDate = queryInfo.getBeginDate();
//        if (beginDate == null) {
//            beginDate = ZDateUtils.today();
//        } else {
////            beginDate = ZDateUtils.today(beginDate);
//        }
//        RangeQueryBuilder timeQuery = QueryBuilders.rangeQuery("timestamp").gte(beginDate);
//        Date endDate = queryInfo.getEndDate();
//        if (endDate != null) {
//            timeQuery.lt(endDate);
//        }
        RangeQueryBuilder timeQuery = getDateRangeQuery("timestamp", queryInfo.getBeginDate(), queryInfo.getEndDate(), false);
        query.must(timeQuery);

        String stackTraceClassName = queryInfo.getStackTraceClassName();
        if (StringUtils.isNotEmpty(stackTraceClassName)) {
            query.must(QueryBuilders.termQuery("stackTraceClassName", stackTraceClassName));
        }

        String stackTraceClassNameLike = queryInfo.getStackTraceClassNameLike();
        if (StringUtils.isNotEmpty(stackTraceClassNameLike)) {
            query.must(QueryBuilders.wildcardQuery("stackTraceClassName", getQueryLikeString(stackTraceClassNameLike)));
        }

        String stackTraceFileName = queryInfo.getStackTraceFileName();
        if (StringUtils.isNotEmpty(stackTraceFileName)) {
            query.must(QueryBuilders.termQuery("stackTraceFileName", stackTraceFileName));
        }

        String stackTraceFileNameLike = queryInfo.getStackTraceFileNameLike();
        if (StringUtils.isNotEmpty(stackTraceFileNameLike)) {
            query.must(QueryBuilders.wildcardQuery("stackTraceFileName", getQueryLikeString(stackTraceFileNameLike)));
        }

        String stackTraceMethodName = queryInfo.getStackTraceMethodName();
        if (StringUtils.isNotEmpty(stackTraceMethodName)) {
            query.must(QueryBuilders.termQuery("stackTraceMethodName", stackTraceMethodName));
        }

        String stackTraceMethodNameLike = queryInfo.getStackTraceMethodNameLike();
        if (StringUtils.isNotEmpty(stackTraceMethodNameLike)) {
            query.must(QueryBuilders.wildcardQuery("stackTraceMethodName", getQueryLikeString(stackTraceMethodNameLike)));
        }

        String stackTraceLineNumber = queryInfo.getStackTraceLineNumber();
        if (StringUtils.isNotEmpty(stackTraceLineNumber)) {
            query.must(QueryBuilders.termQuery("stackTraceLineNumber", stackTraceLineNumber));
        }

        String logType = queryInfo.getLogType();
        if (StringUtils.isNotEmpty(logType)) {
            query.must(QueryBuilders.termQuery("logType", logType));
        }

        String log = queryInfo.getLog();
        if (StringUtils.isNotEmpty(log)) {
            query.must(QueryBuilders.matchQuery("log", log));
        }

        String logLike = queryInfo.getLogLike();
        if (StringUtils.isNotEmpty(logLike)) {
            query.must(QueryBuilders.wildcardQuery("logSource", getQueryLikeString(logLike)));
        }


        return query;
    }

    public boolean deleteAll() {
        boolean suc = client.deleteIndex();
        if (suc) {
            client.createAndMappingIndex(client.getSetting().getMappingClass(), true);
        }
        return suc;
    }

    public boolean deleteAllOld() {
        return client.deleteIndex(DateUtils.lastMonth());
    }

}
