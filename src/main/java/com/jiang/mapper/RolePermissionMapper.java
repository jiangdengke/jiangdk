package com.jiang.mapper;

import com.jiang.pojo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper {
    List<Permission> findPermissionsByRoleId(Long roleId);

}