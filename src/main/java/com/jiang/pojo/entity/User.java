package com.jiang.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * user information
 */
@Data
public class User {
    private Long id;

    private String username;

    private Date createTime;

    private String password;

    private Boolean enable;
}