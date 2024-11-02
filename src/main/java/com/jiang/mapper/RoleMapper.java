package com.jiang.mapper;

import com.jiang.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {
    int insert(Role role);
    Role findRoleById(@Param("id") Long id);
    int deleteRoleByCode(@Param("code") String code);
    int updateRole(@Param("id") Long id,@Param("code") String code,@Param("name") String name);
    Role findRoleByCodeORName(@Param("code") String code,@Param("name") String name);
}