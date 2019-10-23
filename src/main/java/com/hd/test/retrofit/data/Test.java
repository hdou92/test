package com.hd.test.retrofit.data;

/**
 * 测试对象类
 */
public class Test {
    private String code;
    private Integer count;

    public Test() {
    }

    public Test(String code, Integer count) {
        this.code = code;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Test{" +
                "code='" + code + '\'' +
                ", count=" + count +
                '}';
    }
}
