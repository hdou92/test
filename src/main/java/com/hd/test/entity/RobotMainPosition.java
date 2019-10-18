package com.hd.test.entity;

public class RobotMainPosition extends BaseEntity{

    private Office office;

    private OfficeBuilding building;

    private String positionId;

    private String robotModel;

    private String fullName;

    private String name;

    private String type;

    private String floor;

    public RobotMainPosition() {
    }

    public RobotMainPosition(Office office, String positionId, String robotModel, String fullName, String name, String type, String floor) {
        this.office = office;
        this.positionId = positionId;
        this.robotModel = robotModel;
        this.fullName = fullName;
        this.name = name;
        this.type = type;
        this.floor = floor;
    }

    public OfficeBuilding getBuilding() {
        return building;
    }

    public void setBuilding(OfficeBuilding building) {
        this.building = building;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(String robotModel) {
        this.robotModel = robotModel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "RobotMainPosition{" +
                "office=" + office +
                ", positionId='" + positionId + '\'' +
                ", robotModel='" + robotModel + '\'' +
                ", fullName='" + fullName + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", floor='" + floor + '\'' +
                '}';
    }
}
