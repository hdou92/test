package com.hd.test.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 用户类
 * @Data 编译生成get/set方法
 * @TableName 声明表名 不配置 mybatis plus 则获取表名默认按实体名称驼峰获取表名  如： TestUser = test_user
 */
@Data
@TableName("user")
@ToString
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
