package com.jiang.pojo.entity;

import lombok.Data;

/**
 * user role_permission_map
 */
@Data
public class RolePermission {
    private Long id;

    private Long roleId;

    private Long permissionId;
}