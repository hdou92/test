package com.hd.test.elastic.modules.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ElasticRobotStatusPageQuery extends PageQuery {
    @NotNull
    private String officeId;
    @NotNull
    private String robotId;
    /**
     * 开始时间
     */
//    @NotNull
    private Date day;
    /**
     * 结束时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    public ElasticRobotStatusPageQuery() {
    }

    public ElasticRobotStatusPageQuery(int pageIndex, int pageSize, String officeId, String robotId, Date day) {
        super(pageIndex, pageSize);
        this.officeId = officeId;
        this.robotId = robotId;
        this.day = day;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "ElasticRobotStatusPageQuery{" +
                "officeId='" + officeId + '\'' +
                ", robotId='" + robotId + '\'' +
                ", day=" + day +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                "} " + super.toString();
    }
}
