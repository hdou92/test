package com.hd.test.entity;

public class RobotPositionMapping extends BaseEntity {

    private Office office;

    private String datumRobotModel;

    private String datumRobotPositionId;

    private String mappingRobotModel;

    private String mappingRobotPositionId;

    public RobotPositionMapping() {
    }

    public RobotPositionMapping(Office office, String datumRobotModel, String datumRobotPositionId, String mappingRobotModel, String mappingRobotPositionId) {
        this.office = office;
        this.datumRobotModel = datumRobotModel;
        this.datumRobotPositionId = datumRobotPositionId;
        this.mappingRobotModel = mappingRobotModel;
        this.mappingRobotPositionId = mappingRobotPositionId;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getDatumRobotModel() {
        return datumRobotModel;
    }

    public void setDatumRobotModel(String datumRobotModel) {
        this.datumRobotModel = datumRobotModel;
    }

    public String getDatumRobotPositionId() {
        return datumRobotPositionId;
    }

    public void setDatumRobotPositionId(String datumRobotPositionId) {
        this.datumRobotPositionId = datumRobotPositionId;
    }

    public String getMappingRobotModel() {
        return mappingRobotModel;
    }

    public void setMappingRobotModel(String mappingRobotModel) {
        this.mappingRobotModel = mappingRobotModel;
    }

    public String getMappingRobotPositionId() {
        return mappingRobotPositionId;
    }

    public void setMappingRobotPositionId(String mappingRobotPositionId) {
        this.mappingRobotPositionId = mappingRobotPositionId;
    }

    @Override
    public String toString() {
        return "RobotPositionMapping{" +
                "office=" + office +
                ", datumRobotModel='" + datumRobotModel + '\'' +
                ", datumRobotPositionId='" + datumRobotPositionId + '\'' +
                ", mappingRobotModel='" + mappingRobotModel + '\'' +
                ", mappingRobotPositionId='" + mappingRobotPositionId + '\'' +
                '}';
    }
}
