package com.hd.test.entity;

public class OfficeAndModelVO {

    private String officeId;

    private String robotModel;

    public OfficeAndModelVO() {
    }

    public OfficeAndModelVO(String officeId, String robotModel) {
        this.officeId = officeId;
        this.robotModel = robotModel;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(String robotModel) {
        this.robotModel = robotModel;
    }

    @Override
    public String toString() {
        return "OfficeAndModelVO{" +
                "officeId='" + officeId + '\'' +
                ", robotModel='" + robotModel + '\'' +
                '}';
    }
}
