package com.hd.test.elastic.modules.device.entity;

import com.hd.test.elastic.common.ElasticField;
import com.hd.test.elastic.common.ElasticFieldTypeEnum;
import io.searchbox.annotations.JestId;

public class ElasticDispatchLogData implements ElasticModel {
    /**
     * 文档id
     */
    @JestId
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String documentId;
    /**
     * 时间戳
     */
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private long timestamp;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String logType;
    @ElasticField(type = ElasticFieldTypeEnum.TEXT)
    private String log;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String logSource;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String stackTraceClassName;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String stackTraceMethodName;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String stackTraceLineNumber;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String stackTraceFileName;

    @Override
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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getStackTraceClassName() {
        return stackTraceClassName;
    }

    public void setStackTraceClassName(String stackTraceClassName) {
        this.stackTraceClassName = stackTraceClassName;
    }

    public String getStackTraceMethodName() {
        return stackTraceMethodName;
    }

    public void setStackTraceMethodName(String stackTraceMethodName) {
        this.stackTraceMethodName = stackTraceMethodName;
    }

    public String getStackTraceLineNumber() {
        return stackTraceLineNumber;
    }

    public void setStackTraceLineNumber(String stackTraceLineNumber) {
        this.stackTraceLineNumber = stackTraceLineNumber;
    }

    public String getStackTraceFileName() {
        return stackTraceFileName;
    }

    public void setStackTraceFileName(String stackTraceFileName) {
        this.stackTraceFileName = stackTraceFileName;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLogSource() {
        return logSource;
    }

    public void setLogSource(String logSource) {
        this.logSource = logSource;
    }

    @Override
    public String toString() {
        return "ElasticDispatchLogData{" +
                "documentId='" + documentId + '\'' +
                ", timestamp=" + timestamp +
                ", logType='" + logType + '\'' +
                ", log='" + log + '\'' +
                ", logSource='" + logSource + '\'' +
                ", stackTraceClassName='" + stackTraceClassName + '\'' +
                ", stackTraceMethodName='" + stackTraceMethodName + '\'' +
                ", stackTraceLineNumber='" + stackTraceLineNumber + '\'' +
                ", stackTraceFileName='" + stackTraceFileName + '\'' +
                '}';
    }
}
