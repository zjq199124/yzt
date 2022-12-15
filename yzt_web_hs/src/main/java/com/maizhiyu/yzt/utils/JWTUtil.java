package com.maizhiyu.yzt.utils;

import com.maizhiyu.yzt.security.HsUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * className:JWTUtil
 * Package:net.xdclass.util
 * Description:
 *
 * @DATE:2021/8/24 12:46 PM
 * @Author:2101825180@qq.com
 */

@Slf4j
public class JWTUtil {

    /**
     * token 过期时间
     */
    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 7 * 10;

    public static String HEADER = "Authorization";

    /**
     * 加密的密钥
     */
    private static final String SECRET = "hsqwq12";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "hs";

    /**
     * 颁布者
     */
     private static final String SUBJECT = "xdclass";


    /**
     *  根据用户信息生成令牌
     * @param hsUserDetails
     * @return
     */
     public static String geneJsonWebToken(HsUserDetails hsUserDetails) {

         String token = Jwts.builder()
                 .setSubject(SUBJECT)
                 .claim("id", hsUserDetails.getId())
                 .claim("name", hsUserDetails.getNickname())
                 .claim("username", hsUserDetails.getUsername())
                 .claim("customerId", hsUserDetails.getCustomerId())
                 .setIssuedAt(new Date())
                 //指定算法
                 .signWith(SignatureAlgorithm.HS256, SECRET).compact();

         token = TOKEN_PREFIX + token;

         return token;
     }


     public static Claims checkJWT(String token) {
         try {
             Claims claims = Jwts.parser()
                     .setSigningKey(SECRET)
                     .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                     .getBody();

             return claims;
         }catch (Exception e) {
             log.info("jwt token解密失败");
             return null;
         }
     }

    // 从request中获取Claims的字段
    public static Object getFiled(HttpServletRequest request, String key) {
        Claims claims = getClaims(request);
        return Objects.nonNull(claims) ? claims.get(key) : null;
    }

    public static Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Claims getClaims(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            // 截取token
            String token = header.substring(TOKEN_PREFIX.length());
            // 解析token
            return parse(token);
        }
        return null;
    }
}
