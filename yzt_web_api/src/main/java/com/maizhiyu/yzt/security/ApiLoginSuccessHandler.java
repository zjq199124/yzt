package com.maizhiyu.yzt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class ApiLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // 获取信息
        ApiCustomerDetails customerDetails = (ApiCustomerDetails) authentication.getPrincipal();

        // 获取数据
        Map<String, Object> map = new HashMap<>();
        map.put("id", customerDetails.getId());
        map.put("username", customerDetails.getUsername());

        // 生成token
        String jwt = JwtTokenUtils.generate(map);
        String token = JwtTokenUtils.PREFIX + jwt;

        // 设置header
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader(JwtTokenUtils.HEADER, token);

        // 设置body
        response.getWriter().write(objectMapper.writeValueAsString(authentication.getPrincipal()));
    }
}
