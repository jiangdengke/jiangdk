package com.jiang.security.config;

import com.jiang.security.filter.JwtAuthorizationFilter;
import com.jiang.security.filter.JwtLoginAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    /**
     * 定义公共的无需被拦截的资源
     * @return
     */
    private String[] getPubPath(){
        //公共访问资源
        String[] urls = {
                "/**/*.css","/**/*.js","/favicon.ico","/doc.html",
                "/druid/**","/webjars/**","/v2/api-docs","/api/captcha",
                "/swagger/**","/swagger-resources/**","/swagger-ui.html"
        };
        return urls;
    }
    /**
     * 配置过滤规则
     * @param
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity,AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        registry ->
                                registry
                                        //放行的请求
                                        .requestMatchers(getPubPath()).permitAll()
                                        .anyRequest()
                                        .authenticated() //除了上述资源外，其它资源，只有 认证通过后，才能有权访问
                )
                // 将我们自己的usernamePasswordAuthenticationFilter添加到SpringSecurity的过滤器链中
                .addFilterBefore(jwtLoginAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(JwtAuthorizationFilter(), JwtLoginAuthenticationFilter.class);
        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }
    // 暴露 AuthenticationManager 为 Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /**
     * 认证过滤器
     * @return
     * @throws Exception
     */
    @Bean
    public JwtLoginAuthenticationFilter jwtLoginAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        //构造认证管理器，并设置认证路径 /myLogin
        JwtLoginAuthenticationFilter authenticationFilter = new JwtLoginAuthenticationFilter("/login");
        authenticationFilter.setAuthenticationManager(authenticationManager);
        return authenticationFilter;
    }
    /**
     * 授权过滤器
     * 检查jwt的票据是否有效，做相关处理
     */
    @Bean
    public JwtAuthorizationFilter JwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }
}
