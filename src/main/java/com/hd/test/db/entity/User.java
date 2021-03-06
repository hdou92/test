package com.hd.test.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户类
 * 使用 lombok 的时候 有时候会出现对象为空的情况  不知道为啥
 * @TableName 声明表名 不配置 mybatis plus 则获取表名默认按实体名称驼峰获取表名  如： TestUser = test_user
 */
@TableName("user")
//@Getter
//@Setter
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
