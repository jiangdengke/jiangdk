package com.jiang.pojo.entity;

import lombok.Data;

/**
 * user user_role_map
 */
@Data
public class UserRole {
    private Long id;

    private Long userId;

    private Long roleId;
}