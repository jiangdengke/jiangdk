package com.jiang.mapper;

import com.jiang.pojo.entity.Permission;
import com.jiang.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    List<Permission> getPermissionsByUsername(String username);
}