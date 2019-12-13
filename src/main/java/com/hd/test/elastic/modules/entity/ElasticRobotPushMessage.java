package com.hd.test.elastic.modules.entity;

import com.hd.test.elastic.common.ElasticField;
import com.hd.test.elastic.common.ElasticFieldTypeEnum;
import io.searchbox.annotations.JestId;

public class ElasticRobotPushMessage implements ElasticModel {
    /**
     * 文档id
     */
    @JestId
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String documentId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String robotId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String officeId;
    /**
     * 消息时间戳
     */
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private long timestamp;
    /**
     * 消息id
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String msgId;
    /**
     * 消息路径
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String path;
    /**
     * 消息内容
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String body;
    /**
     * 消息推送是否成功
     */
    @ElasticField(type = ElasticFieldTypeEnum.BOOLEAN)
    private Boolean success;
    /**
     * 第一次发送时的消息时间戳
     */
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private long firstTimestamp;
    /**
     * 发送的次数
     */
    @ElasticField(type = ElasticFieldTypeEnum.INTEGER)
    private int sendCount;

    @Override
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public long getFirstTimestamp() {
        return firstTimestamp;
    }

    public void setFirstTimestamp(long firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    @Override
    public String toString() {
        return "ElasticRobotPushMessage{" +
                "documentId='" + documentId + '\'' +
                ", robotId='" + robotId + '\'' +
                ", officeId='" + officeId + '\'' +
                ", timestamp=" + timestamp +
                ", msgId='" + msgId + '\'' +
                ", path='" + path + '\'' +
                ", body='" + body + '\'' +
                ", success=" + success +
                ", firstTimestamp=" + firstTimestamp +
                ", sendCount=" + sendCount +
                '}';
    }
}
