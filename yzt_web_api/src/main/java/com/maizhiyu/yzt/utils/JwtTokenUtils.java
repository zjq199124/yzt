package com.maizhiyu.yzt.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Slf4j
@Component
public class JwtTokenUtils {

    public static String HEADER = "Authorization";

    public static String PREFIX = "Bearer ";

    public static String SECRET = "123456";

    public static Long EXPIRE = 60 * 60L;


    @Value("${jwt.header}")
    public void setHEADER(String HEADER) {
        JwtTokenUtils.HEADER = HEADER;
    }

    @Value("${jwt.prefix}")
    public void setPREFIX(String PREFIX) {
        JwtTokenUtils.PREFIX = PREFIX;
    }

    @Value("${jwt.secret}")
    public void setSECRET(String SECRET) {
        JwtTokenUtils.SECRET = SECRET;
    }

    @Value("${jwt.expire}")
    public void setEXPIRE(Long EXPIRE) {
        JwtTokenUtils.EXPIRE = EXPIRE;
    }


    // 生成JWT(claims是携带的信息)
    public static String generate(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRE))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }


    // 解析JWT生成Claims
    public static Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // 从request中获取Claims
    public static Claims getClaims(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        if (header != null && header.startsWith(PREFIX)) {
            // 截取token
            String token = header.substring(JwtTokenUtils.PREFIX.length());
            // 解析token
            return JwtTokenUtils.parse(token);
        }
        return null;
    }

    // 从request中获取Claims的字段
    public static Object getField(HttpServletRequest request, String key) {
        Claims claims = getClaims(request);
        if (claims != null) {
            return claims.get(key);
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("customer", "aaa");
        map.put("data", "xxx");

        String jwt = JwtTokenUtils.generate(map);
        System.out.println(jwt);

        Claims claims = JwtTokenUtils.parse(jwt);
        System.out.println(claims);
    }
}
