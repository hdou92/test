package com.hd.test.entity;

/**
 * 测试楼宇
 */
public class OfficeBuilding {

    private String id;

    private String name;

    public OfficeBuilding() {
    }

    public OfficeBuilding(String id) {
        this.id = id;
    }

    public OfficeBuilding(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OfficeBuilding{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
