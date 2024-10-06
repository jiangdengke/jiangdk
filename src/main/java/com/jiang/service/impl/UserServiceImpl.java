package com.jiang.service.impl;

import com.jiang.mapper.RolePermissionMapper;
import com.jiang.mapper.UserMapper;
import com.jiang.mapper.UserRoleMapper;
import com.jiang.pojo.entity.Permission;
import com.jiang.pojo.entity.User;
import com.jiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public User getUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        List<Long> roleIds = userRoleMapMapper.findRoleIdByUserId(userId);
        return roleIds;
    }

    /**
     * 根据roleId查所有的权限信息。
     *
     * @param roleId
     */
    @Override
    public List<String> getPermissionsByRoleId(Long roleId) {
        List<Permission> Permissions = rolePermissionMapper.findPermissionsByRoleId(roleId);
        List<String> collect = Permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
        return collect;
    }

}
