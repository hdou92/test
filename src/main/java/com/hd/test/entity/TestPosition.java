package com.hd.test.entity;

public class TestPosition extends BaseEntity {

    private String name;

    private String fullName;

    private String type = "1";

    private Double x = 5d;

    private Double y = 5d;

    private Double z = 5d;

    private String floor = "9";

    private OfficeBuilding building;

    private Office office;

    private String guId;

    private String robotModel;

    public TestPosition() {
    }

    public TestPosition(String name, OfficeBuilding building, Office office, String guId, String robotModel) {
        this.name = name;
        this.fullName = name + "_" + floor + "_" + type;
        this.building = building;
        this.office = office;
        this.guId = guId;
        this.robotModel = robotModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
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

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(String robotModel) {
        this.robotModel = robotModel;
    }

    @Override
    public String toString() {
        return "TestPosition{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", floor='" + floor + '\'' +
                ", building=" + building +
                ", office=" + office +
                ", guId='" + guId + '\'' +
                ", robotModel='" + robotModel + '\'' +
                '}';
    }
}
