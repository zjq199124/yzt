package com.maizhiyu.yzt.security;


import com.alibaba.fastjson.JSONObject;
import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.util.JwtUtil;
import com.maizhiyu.yzt.util.RedisCache;
import com.maizhiyu.yzt.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author zjq
 * @date 2023-03-02
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private  RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("授权令牌非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
//        MsUserDetails loginUser =(MsUserDetails) redisUtils.get(redisKey);
//        MsUser user  = redisCache.getCacheObject(redisKey);
        MsUserDetails loginUser = JSONObject.toJavaObject(redisCache.getCacheObject(redisKey), MsUserDetails.class);
//        MsUserDetails loginUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
