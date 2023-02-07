package com.maizhiyu.yzt.utils;

import com.maizhiyu.yzt.security.HsUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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
                 .claim("name", hsUserDetails.getNickName())
                 .claim("username", hsUserDetails.getUsername())
                 .claim("customerId", hsUserDetails.getCustomerId())
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
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
}
