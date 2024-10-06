package com.jiang.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.pojo.vo.req.LoginReqVo;
import com.jiang.pojo.vo.resp.Result;
import com.jiang.security.user.LoginUserDetail;
import com.jiang.security.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public JwtLoginAuthenticationFilter(String loginUrl) {
        super(loginUrl);
    }

    /**
     * 尝试认证的方法
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        //请求必须是post请求
        if (!request.getMethod().equalsIgnoreCase("POST") || ! (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType()) || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(request.getContentType())) ) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 使用 ObjectMapper 读取请求体中的 JSON
        String requestBody = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        LoginReqVo reqVo = new ObjectMapper().readValue(requestBody,LoginReqVo.class);

        // 这里写验证码等验证逻辑
        String username = reqVo.getUsername();
        username = username != null ? username : "";
        username = username.trim();
        String password = reqVo.getPassword();
        password = password != null ? password : "";
        // 假设验证码通过
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 认证成功执行的方法
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUserDetail principal = (LoginUserDetail)authResult.getPrincipal();
        String username = principal.getUsername();
        List<GrantedAuthority> authorities = principal.getAuthorities();
        // 生成token
        String token = JwtTokenUtil.createToken(username, authorities.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Result<String> success = Result.success(token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(success));
    }

    /**
     * 认证失败后执行的方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Result error = Result.error("401","Authentication failed");
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}