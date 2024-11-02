package com.jiang.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.security.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头获取token字符串
        String tokenStr = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //判断是否为空
        if (StringUtils.isBlank(tokenStr)) {
            //如果票据为空，则放行，此时安全上下文中肯定没有这个票据，后续的过滤器也得不到这个票据
            filterChain.doFilter(request,response);
            return;
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        //检查是否合法
        String check = JwtTokenUtil.checkJWT(tokenStr);
//        if (claims==null) {
//            //票据不合法，过滤器终止。
//            Result error = Result.error("401",ResponseCode.UNAUTHORIZED_Token.toString());
////            R error = R.error(ResponseCode.INVALID_TOKEN);
//            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
//            return;
//        }
        if (!check.equals("token验证通过")){
            response.getWriter().write(new ObjectMapper().writeValueAsString(check));
            return;
        }

        String username = JwtTokenUtil.getUsername(tokenStr);
        String roles = JwtTokenUtil.getUserRole(tokenStr);
        String strip = StringUtils.strip(roles, "[]");
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strip);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
        //将封装的票据存到安全上下文，这样后续的认证过滤器就可以从安全上下文中获取权限信息
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //发行请求
        filterChain.doFilter(request,response);
    }
}
