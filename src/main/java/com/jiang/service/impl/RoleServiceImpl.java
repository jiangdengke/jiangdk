package com.jiang.service.impl;

import com.jiang.exception.BusinessException;
import com.jiang.mapper.RoleMapper;
import com.jiang.pojo.entity.Role;
import com.jiang.pojo.vo.resp.ResponseCode;
import com.jiang.pojo.vo.resp.Result;
import com.jiang.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: JiangDk
 * @date: 2024/10/6 21:40
 * @description:
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 创建角色
     *
     * @return
     */
    @Override
    public Result<String> createRole(Role role) {
        if (role.getCode()==null||role.getName()==null){
            throw new BusinessException("角色Code或Name为空");
        }
        // 查看角色有没有创建
        Role oldRole = roleMapper.findRoleByCodeORName( role.getName(), role.getCode());
        if (oldRole!=null){
            throw new BusinessException(ResponseCode.ROLE_EXISTS_ERROR.getMessage());
        }
        int insert = roleMapper.insert(role);
        if (insert!=1){
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return Result.success(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 删除角色根据角色Code
     * @param code
     * @return
     */
    @Override
    public Result<String> deleteRole(String code) {
        if (StringUtils.isBlank(code)){
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
        // 删除用户
        int deleteRoleByCode = roleMapper.deleteRoleByCode(code);
        if (deleteRoleByCode!=1){
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return Result.success(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 更新角色信息
     * @param id
     * @param code
     * @param name
     * @return
     */
    @Override
    public Result<String> updateRole(Long id, String code, String name) {
        if (id == null || StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
        int i = roleMapper.updateRole(id, code, name);
        if (i!=1){
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return Result.success(ResponseCode.SUCCESS.getMessage());
    }

    /**查询角色信息
     * @param id
     * @return
     */
    @Override
    public Result<Role> findRoleById(Long id) {
        if (id==null){
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
        Role role = roleMapper.findRoleById(id);
        return Result.success(role);
    }


}
