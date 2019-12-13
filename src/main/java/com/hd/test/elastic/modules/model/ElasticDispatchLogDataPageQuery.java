package com.hd.test.elastic.modules.model;

import java.util.Date;

public class ElasticDispatchLogDataPageQuery extends PageQuery {
    private Date beginDate;
    private Date endDate;
    private String logType;
    private String stackTraceClassName;
    private String stackTraceClassNameLike;
    private String stackTraceMethodName;
    private String stackTraceMethodNameLike;
    private String stackTraceLineNumber;
    private String stackTraceFileName;
    private String stackTraceFileNameLike;
    private String log;
    private String logLike;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getStackTraceClassNameLike() {
        return stackTraceClassNameLike;
    }

    public void setStackTraceClassNameLike(String stackTraceClassNameLike) {
        this.stackTraceClassNameLike = stackTraceClassNameLike;
    }

    public String getStackTraceMethodName() {
        return stackTraceMethodName;
    }

    public void setStackTraceMethodName(String stackTraceMethodName) {
        this.stackTraceMethodName = stackTraceMethodName;
    }

    public String getStackTraceMethodNameLike() {
        return stackTraceMethodNameLike;
    }

    public void setStackTraceMethodNameLike(String stackTraceMethodNameLike) {
        this.stackTraceMethodNameLike = stackTraceMethodNameLike;
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

    public String getStackTraceFileNameLike() {
        return stackTraceFileNameLike;
    }

    public void setStackTraceFileNameLike(String stackTraceFileNameLike) {
        this.stackTraceFileNameLike = stackTraceFileNameLike;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLogLike() {
        return logLike;
    }

    public void setLogLike(String logLike) {
        this.logLike = logLike;
    }

    @Override
    public String toString() {
        return "ElasticDispatchLogDataPageQuery{" +
                "beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", logType='" + logType + '\'' +
                ", stackTraceClassName='" + stackTraceClassName + '\'' +
                ", stackTraceClassNameLike='" + stackTraceClassNameLike + '\'' +
                ", stackTraceMethodName='" + stackTraceMethodName + '\'' +
                ", stackTraceMethodNameLike='" + stackTraceMethodNameLike + '\'' +
                ", stackTraceLineNumber='" + stackTraceLineNumber + '\'' +
                ", stackTraceFileName='" + stackTraceFileName + '\'' +
                ", stackTraceFileNameLike='" + stackTraceFileNameLike + '\'' +
                ", log='" + log + '\'' +
                ", logLike='" + logLike + '\'' +
                "} " + super.toString();
    }
}
