package com.jiang.service;

import com.jiang.pojo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    /**
     * 根据username查User
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据userid查所有的roleid
     * @param userId
     * @return
     */
    List<Long> getRoleIdsByUserId(Long userId);
    /**
     * 根据roleId查所有的权限信息。
     */
    List<String> getPermissionsByRoleId(Long roleId);
}


