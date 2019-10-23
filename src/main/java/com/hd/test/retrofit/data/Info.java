package com.hd.test.retrofit.data;

import java.util.List;

/**
 * 测试对象
 */
public class Info<T> {
    private String name;
    private List<T> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
