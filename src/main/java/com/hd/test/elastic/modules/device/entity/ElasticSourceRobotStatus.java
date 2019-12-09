package com.hd.test.elastic.modules.device.entity;

import com.hd.test.elastic.common.ElasticField;
import com.hd.test.elastic.common.ElasticFieldTypeEnum;
import io.searchbox.annotations.JestId;

/**
 * elastic的RobotStatus的原始数据
 */
public class ElasticSourceRobotStatus implements ElasticModel {

    /**
     * 文档id
     */
    @JestId
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String documentId;
    /**
     * 原始数据记录
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String sourceMsg;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String robotId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String officeId;
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private Long lastUploadTime;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getSourceMsg() {
        return sourceMsg;
    }

    public void setSourceMsg(String sourceMsg) {
        this.sourceMsg = sourceMsg;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public Long getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(Long lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    @Override
    public String toString() {
        return "ElasticSourceRobotStatus{" +
                "documentId='" + documentId + '\'' +
                ", sourceMsg='" + sourceMsg + '\'' +
                ", robotId='" + robotId + '\'' +
                ", officeId='" + officeId + '\'' +
                ", lastUploadTime=" + lastUploadTime +
                '}';
    }
}
