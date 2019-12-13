package com.hd.test.elastic.modules.entity;

import com.hd.test.elastic.common.ElasticField;
import com.hd.test.elastic.common.ElasticFieldTypeEnum;
import io.searchbox.annotations.JestId;

/**
 * 测试用户模型实体
 */
public class ElasticTestUserModel implements ElasticModel {
    /**
     * 文档id
     */
    @JestId
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String documentId;
    /**
     * 用户名
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String username;
    /**
     * 年龄
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String age;
    /**
     * 性别
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private long gender;
    /**
     * 简介
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String about;
    /**
     * 地址
     */
    @ElasticField(type = ElasticFieldTypeEnum.ARRAY)
    private String addrs;
    /**
     * 创建时间
     */
    @ElasticField(type = ElasticFieldTypeEnum.KEYWORD)
    private String createDate;

    @Override
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public long getGender() {
        return gender;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddrs() {
        return addrs;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ElasticTestUserModel{" +
                "documentId='" + documentId + '\'' +
                ", username='" + username + '\'' +
                ", age='" + age + '\'' +
                ", gender=" + gender +
                ", about='" + about + '\'' +
                ", addrs='" + addrs + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
