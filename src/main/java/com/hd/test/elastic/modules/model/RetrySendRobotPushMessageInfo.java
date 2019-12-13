package com.hd.test.elastic.modules.model;

import com.hd.test.elastic.modules.entity.ElasticRobotPushMessage;

public class RetrySendRobotPushMessageInfo {
    private String documentId;
    private long timestamp;
    private Boolean success;
    private int sendCount;

    public RetrySendRobotPushMessageInfo() {
    }

    public RetrySendRobotPushMessageInfo(ElasticRobotPushMessage msg) {
        this.documentId = msg.getDocumentId();
        this.timestamp = msg.getTimestamp();
        this.success = msg.getSuccess();
        this.sendCount = msg.getSendCount();
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    @Override
    public String toString() {
        return "RetrySendRobotPushMessageInfo{" +
                "documentId='" + documentId + '\'' +
                ", timestamp=" + timestamp +
                ", success=" + success +
                ", sendCount=" + sendCount +
                '}';
    }
}
