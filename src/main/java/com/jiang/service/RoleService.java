package com.jiang.service;

import com.jiang.pojo.entity.Role;
import com.jiang.pojo.vo.resp.Result;

/**
 * @author: JiangDk
 * @date: 2024/10/6 21:40
 * @description:
 */
public interface RoleService {
    /**
     * 创建角色
     * @param
     * @return
     */
    Result<String> createRole(Role role);

    /**
     * 删除角色
     * @param code
     * @return
     */
    Result<String> deleteRole(String code);

    /**
     * 更新角色
     * @param id
     * @param code
     * @param name
     * @return
     */
    Result<String> updateRole(Long id,String code,String name);

    /**
     *
     * @param id
     * @return
     */
    Result<Role> findRoleById(Long id);

}
