package com.jiang.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    List<Long> findRoleIdByUserId(Long userId);
}