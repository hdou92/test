package com.hd.test.elastic.modules.service;

import com.hd.test.common.DateUtils;
import com.hd.test.common.StringUtils;
import com.hd.test.elastic.config.ElasticIndexConsts;
import com.hd.test.elastic.manager.ElasticManager;
import com.hd.test.elastic.modules.entity.ElasticSourceRobotStatus;
import com.hd.test.elastic.modules.model.ElasticRobotStatusPageQuery;
import com.hd.test.elastic.service.ElasticServiceBase;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 机器人原始状态记录
 */
//@Service
public class ElasticSourceRobotStatusService extends ElasticServiceBase<ElasticSourceRobotStatus> {
    private static final String TIME_NAME = "lastUploadTime";

    public ElasticSourceRobotStatusService(ElasticManager elasticManager) {
        super(ElasticIndexConsts.SOURCE_ROBOT_STATUS_INDEX_NAME, ElasticSourceRobotStatus.class, elasticManager);
    }

    /**
     * 统计
     */
    public long countBy(ElasticRobotStatusPageQuery queryInfo) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(queryInfo.getRobotId())) {
            query.must(QueryBuilders.termQuery("robotId", queryInfo.getRobotId()));
        } else if (StringUtils.isNotEmpty(queryInfo.getOfficeId())) {
            query.must(QueryBuilders.termQuery("officeId", queryInfo.getOfficeId()));
        }


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

}
