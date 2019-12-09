package com.hd.test.elastic.modules.device.service;

import com.hd.test.common.DateUtils;
import com.hd.test.common.ModelUtils;
import com.hd.test.common.ObjectUtils;
import com.hd.test.common.StringUtils;
import com.hd.test.elastic.common.ElasticPageInfo;
import com.hd.test.elastic.config.ElasticIndexConsts;
import com.hd.test.elastic.manager.ElasticManager;
import com.hd.test.elastic.modules.device.entity.ElasticRobotPushMessage;
import com.hd.test.elastic.modules.device.model.ElasticRobotPushMessagePageQuery;
import com.hd.test.elastic.modules.device.model.RetrySendRobotPushMessageInfo;
import com.hd.test.elastic.service.ElasticServiceBase;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ElasticRobotPushMessageService extends ElasticServiceBase<ElasticRobotPushMessage> {
    private static final String TIME_NAME = "timestamp";

    public ElasticRobotPushMessageService(ElasticManager elasticManager) {
        super(ElasticIndexConsts.ROBOT_PUSH_MESSAGE_INDEX_NAME, ElasticRobotPushMessage.class, elasticManager);
    }

    /**
     * 分页查询
     */
    public ElasticPageInfo<ElasticRobotPushMessage> findPage(ElasticRobotPushMessagePageQuery queryInfo) {
        BoolQueryBuilder query = getQuery(queryInfo);

//        Date beginDate = null;
//        Date endDate = queryInfo.getDay();
//        if (endDate == null) {
//            beginDate = ZDateUtils.today();
//            endDate = new Date();
//        } else {
//            beginDate = ZDateUtils.today(beginDate);
//        }
////        Date endDate = ZDateUtils.addDays(beginDate, 1);
//        query.must(QueryBuilders.rangeQuery(TIME_NAME).gte(beginDate).lt(endDate));

        Date day = null;
        if (queryInfo.getDay() != null) {
            day = queryInfo.getDay();
            query.must(getTodayQuery(TIME_NAME, queryInfo.getDay()));
        } else {
            day = ObjectUtils.getObject(queryInfo.getStartDate(), queryInfo.getEndDate());
            query.must(getDateRangeQuery(TIME_NAME, queryInfo.getStartDate(), queryInfo.getEndDate(), true));
        }

        return client.findPageByIndex(day, query, TIME_NAME, SortOrder.DESC,
                queryInfo.getPageIndex(), queryInfo.getPageSize(), ElasticRobotPushMessage.class);
    }

    /**
     * 统计
     */
    public long countBy(ElasticRobotPushMessagePageQuery queryInfo) {
        BoolQueryBuilder query = getQuery(queryInfo);

        Date beginDate = queryInfo.getDay();
        if (beginDate != null) {
            beginDate = DateUtils.today(beginDate);
//            Date endDate = ZDateUtils.addDays(beginDate, 1);
            Date endDate = queryInfo.getDay();
            query.must(QueryBuilders.rangeQuery(TIME_NAME).gte(beginDate).lt(endDate));
        } else if (queryInfo.getStartDate() != null || queryInfo.getEndDate() != null) {
            beginDate = queryInfo.getStartDate();
            query.must(getDateRangeQuery(TIME_NAME, beginDate, queryInfo.getEndDate(), true));
        }

        return client.countByIndex(beginDate, query, TIME_NAME, SortOrder.DESC);
    }

    private BoolQueryBuilder getQuery(ElasticRobotPushMessagePageQuery queryInfo) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(queryInfo.getRobotId())) {
            query.must(QueryBuilders.termQuery("robotId", queryInfo.getRobotId()));
        } else {
            query.must(QueryBuilders.termQuery("officeId", queryInfo.getOfficeId()));
        }

        if (queryInfo.getPath() != null) {
            query.must(QueryBuilders.termQuery("path", queryInfo.getPath()));
        }

        if (queryInfo.getSuccess() != null) {
            if (queryInfo.getSuccess()) {
                query.must(QueryBuilders.termQuery("success", true));
            } else {
                query.mustNot(QueryBuilders.termQuery("success", true));    //为了获取null的
            }
        }

        if (queryInfo.getSendCount() != null) {
            query.must(QueryBuilders.rangeQuery("sendCount").gte(queryInfo.getSendCount()));    // >= sendCount
        }
        return query;
    }

    /**
     * 更新重新发送的信息
     */
    public boolean updateRetrySend(RetrySendRobotPushMessageInfo info) {
        Map<String, Object> map = ModelUtils.objectToMap(info);
        map.remove("documentId");   //docId不需要更新的
        return client.update(info.getDocumentId(), map, pair -> {
            String sourceField = "ctx._source." + pair.getKey();
            if (pair.getKey().equals("sendCount")) {
                //如果为sendCount，那么应该是相加值
                return sourceField + " = params." + pair.getKey() + " + " + sourceField + ";";
            } else {
                return sourceField + " = params." + pair.getKey() + ";";
            }
        });
    }

}
