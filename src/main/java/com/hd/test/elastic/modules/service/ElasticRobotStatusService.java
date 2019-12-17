package com.hd.test.elastic.modules.service;

import com.hd.test.common.DateUtils;
import com.hd.test.common.ObjectUtils;
import com.hd.test.common.StringUtils;
import com.hd.test.elastic.common.ElasticPageInfo;
import com.hd.test.elastic.config.ElasticIndexConsts;
import com.hd.test.elastic.manager.ElasticManager;
import com.hd.test.elastic.modules.entity.ElasticRobotStatus;
import com.hd.test.elastic.modules.model.ElasticRobotStatusPageQuery;
import com.hd.test.elastic.service.ElasticServiceBase;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 机器人状态service
 */
//@Service
public class ElasticRobotStatusService extends ElasticServiceBase<ElasticRobotStatus> {
    private static final String TIME_NAME = "lastUploadTime";

    public ElasticRobotStatusService(ElasticManager elasticManager) {
        super(ElasticIndexConsts.ROBOT_STATUS_INDEX_NAME, ElasticRobotStatus.class, elasticManager);
    }

    /**
     * 分页查询
     */
    public ElasticPageInfo<ElasticRobotStatus> findPage(ElasticRobotStatusPageQuery queryInfo) {
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

        return client.findPageByIndex(day, query, TIME_NAME, SortOrder.DESC, queryInfo.getPageIndex(), queryInfo.getPageSize(), ElasticRobotStatus.class);
    }

    /**
     * 统计
     */
    public long countBy(ElasticRobotStatusPageQuery queryInfo) {
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

    private BoolQueryBuilder getQuery(ElasticRobotStatusPageQuery queryInfo) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(queryInfo.getRobotId())) {
            query.must(QueryBuilders.termQuery("robotId", queryInfo.getRobotId()));
        } else {
            query.must(QueryBuilders.termQuery("officeId", queryInfo.getOfficeId()));
        }
        return query;
    }

}
