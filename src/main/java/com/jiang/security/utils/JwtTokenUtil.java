package com.jiang.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    // Token请求头
    public static final String TOKEN_HEADER = "authorization";
    // 过期时间,单位毫秒
    public static final long EXPIRITION = 1000 * 60 * 60* 24 * 7;
    // 应用密钥
    public static final String APPSECRET_KEY = "jiangdk";
    private static final String ROLE_CLAIMS = "role";

    /**
     * 生成Token
     */
    public static String createToken(String username,String role) {
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }
    /**
     * 校验Token
     * 该方法主要用于验证JWT（JSON Web Token）的合法性和完整性
     * 它通过指定的签名密钥解析Token，并返回Token中的声明（claims）
     * 如果Token无效或解析过程中发生任何异常，则返回null
     *
     * @param token 待验证的JWT字符串
     * @return 如果Token有效，则返回JWT中的声明对象；否则返回null
     */
    public static String checkJWT(String token) {
        String ans = "token验证通过";
       // 通过秘钥拿到jwt的载荷部分。
        Claims claims = Jwts
                .parser()
                .setSigningKey(APPSECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        if (claims==null){
            ans = "票据不合法";
            return ans;
        }
        boolean expiration = isExpiration(token);
        if (expiration==true){
            ans = "token已过期";
        }
        return ans;
    }
    /**
     * 从Token中获取用户名
     */
    public static String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 从Token中获取用户角色
     *
     * @param token 用户的Token字符串，用于提取用户角色信息
     * @return 用户角色字符串，表示用户在系统中的角色
     */
    public static String getUserRole(String token){
        // 解析Token，验证签名并获取载荷（claims）
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        // 从载荷中获取用户角色并返回
        return claims.get("role").toString();
    }

    /**
     * 校验Token是否过期
     *
     * @param token 待校验的Token字符串
     * @return 返回一个布尔值，如果Token已经过期则返回true，否则返回false
     */
    public static boolean isExpiration(String token){
        // 解析Token并获取其中的声明部分
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        // 比较声明中的过期时间与当前时间，判断Token是否过期
        return claims.getExpiration().before(new Date());
    }

}
