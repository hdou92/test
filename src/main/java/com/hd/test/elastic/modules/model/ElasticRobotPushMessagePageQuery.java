package com.hd.test.elastic.modules.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ElasticRobotPushMessagePageQuery extends PageQuery {
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
    /**
     * 消息路径
     */
    private String path;
    /**
     * 消息推送是否成功
     */
    private Boolean success;
    /**
     * 发送次数
     */
    private Integer sendCount;

    public ElasticRobotPushMessagePageQuery() {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    @Override
    public String toString() {
        return "ElasticRobotPushMessagePageQuery{" +
                "officeId='" + officeId + '\'' +
                ", robotId='" + robotId + '\'' +
                ", day=" + day +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", path='" + path + '\'' +
                ", success=" + success +
                ", sendCount=" + sendCount +
                "} " + super.toString();
    }
}
