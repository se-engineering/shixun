package com.example.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

//使用lombok自动生成get和set方法
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long userId;

    private String phone;

    private String password;

    private String nickname;

    private String salt;

    private Integer state;

    private Date createTime;

    private Date updateTime;



}
