package com.nsulzh.sso.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String name;
    private Integer age;
    private String passWord;
    private Date birthDay;
    private String desc;
}
