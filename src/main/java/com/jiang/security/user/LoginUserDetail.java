package com.jiang.security.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
@Data
public class LoginUserDetail implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
}
