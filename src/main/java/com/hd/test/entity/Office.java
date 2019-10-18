package com.hd.test.entity;

/**
 * 测试机构
 */
public class Office {

    private String id;

    public Office() {
    }

    public Office(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Office{" +
                "id='" + id + '\'' +
                '}';
    }
}
