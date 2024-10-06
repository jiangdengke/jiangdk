package com.jiang.security.service;

import com.jiang.pojo.entity.User;
import com.jiang.security.user.LoginUserDetail;
import com.jiang.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class loginUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 由认证管理器解析之后的username被传到了这里
        User user = userService.getUserByUsername(username);
        // 判断该用户是否存在
        if (user==null){
            throw new UsernameNotFoundException("用户名输入错误！");
        }
        // 组装UserDetail对象,
        List<Long> roleIds = userService.getRoleIdsByUserId(user.getId());// 获取所有的角色id
        List<String> permissionNames = new ArrayList<>();
        for (Long roleId : roleIds) {
            List<String> rolePermissions = userService.getPermissionsByRoleId(roleId);
            permissionNames.addAll(rolePermissions);
        }
        // 对拿到的所有权限信息进行去重
        List<String> uniquePermissions = permissionNames.stream()
                .distinct()
                .collect(Collectors.toList());
        // 拿到以逗号分隔的权限列表
        String userRoleString = uniquePermissions.stream()
                .collect(Collectors.joining(","));
        List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList(userRoleString);
        LoginUserDetail loginUserDetail = new LoginUserDetail();
        BeanUtils.copyProperties(user,loginUserDetail);// 把查到的对象属性都赋给UserDetails
        loginUserDetail.setPassword("{noop}"+user.getPassword());
        loginUserDetail.setUsername(username);
        loginUserDetail.setAuthorities(list);
        return loginUserDetail;
    }
}