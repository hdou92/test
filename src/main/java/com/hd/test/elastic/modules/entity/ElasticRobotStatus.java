package com.hd.test.elastic.modules.entity;

import com.hd.test.elastic.common.ElasticField;
import com.hd.test.elastic.common.ElasticFieldTypeEnum;
import io.searchbox.annotations.JestId;

import java.util.Date;
import java.util.List;

/**
 * elastic的RobotStatus数据对象
 */
public class ElasticRobotStatus implements ElasticModel {
    /**
     * 文档id
     */
    @JestId
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String documentId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String robotId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String robotName;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String robotAccount;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String officeId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String officeName;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    /**
     * 机器人型号
     */
    private String robotModel;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String buildingName;
    /**
     * 楼宇ID
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String buildingId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String statusText;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String jobId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String groupId;
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private Long lastUploadTime;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String x;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String y;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String z;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String orientation;
    /**
     * 最后一个位置
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String spotId;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String spotName;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String target;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String targetName;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private List<String> process;//线路规则中要经过的位置
    /**
     * 下一个位置
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String nextSpot;
    /**
     * 楼层
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String floor;
    /**
     * 电量
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String electric;
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD, index = false)
    private String message;
    /**
     * 开始空闲的时间
     */
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private Date startIdleTime;
    /**
     * 开始异常时间
     */
    @ElasticField(type = ElasticFieldTypeEnum.DATE)
    private Date startErrorTime;
    @ElasticField(type = ElasticFieldTypeEnum.INTEGER)
    private Integer jobType;
    /**
     * 是否暂停：1：暂停，0：正常
     */
    @ElasticField(type = ElasticFieldTypeEnum.INTEGER)
    private Integer pauseType;

    /**
     * 是否急停
     * 0-正常，1-急停状态
     */
    @ElasticField(type = ElasticFieldTypeEnum.INTEGER)
    private Integer estopStatus;


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

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(Long lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public List<String> getProcess() {
        return process;
    }

    public void setProcess(List<String> process) {
        this.process = process;
    }

    public String getNextSpot() {
        return nextSpot;
    }

    public void setNextSpot(String nextSpot) {
        this.nextSpot = nextSpot;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getStartIdleTime() {
        return startIdleTime;
    }

    public void setStartIdleTime(Date startIdleTime) {
        this.startIdleTime = startIdleTime;
    }

    public Date getStartErrorTime() {
        return startErrorTime;
    }

    public void setStartErrorTime(Date startErrorTime) {
        this.startErrorTime = startErrorTime;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getPauseType() {
        return pauseType;
    }

    public void setPauseType(Integer pauseType) {
        this.pauseType = pauseType;
    }

    public String getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(String robotModel) {
        this.robotModel = robotModel;
    }

    public Integer getEstopStatus() {
        return estopStatus;
    }

    public void setEstopStatus(Integer estopStatus) {
        this.estopStatus = estopStatus;
    }

    public String getRobotAccount() {
        return robotAccount;
    }

    public void setRobotAccount(String robotAccount) {
        this.robotAccount = robotAccount;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "ElasticRobotStatus{" +
                "documentId='" + documentId + '\'' +
                ", robotId='" + robotId + '\'' +
                ", robotName='" + robotName + '\'' +
                ", robotAccount='" + robotAccount + '\'' +
                ", officeId='" + officeId + '\'' +
                ", officeName='" + officeName + '\'' +
                ", robotModel='" + robotModel + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", statusText='" + statusText + '\'' +
                ", jobId='" + jobId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", lastUploadTime=" + lastUploadTime +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                ", orientation='" + orientation + '\'' +
                ", spotId='" + spotId + '\'' +
                ", spotName='" + spotName + '\'' +
                ", target='" + target + '\'' +
                ", targetName='" + targetName + '\'' +
                ", process=" + process +
                ", nextSpot='" + nextSpot + '\'' +
                ", floor='" + floor + '\'' +
                ", electric='" + electric + '\'' +
                ", message='" + message + '\'' +
                ", startIdleTime=" + startIdleTime +
                ", startErrorTime=" + startErrorTime +
                ", jobType=" + jobType +
                ", pauseType=" + pauseType +
                ", estopStatus=" + estopStatus +
                '}';
    }
}
