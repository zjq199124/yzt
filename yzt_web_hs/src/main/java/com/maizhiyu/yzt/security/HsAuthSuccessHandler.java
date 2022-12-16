package com.maizhiyu.yzt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizhiyu.yzt.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class HsAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("登录成功。。。。。。");
        response.setContentType("application/json;charset=UTF-8");
        HsUserDetails hsUserDetails = (HsUserDetails) authentication.getPrincipal();
        String token = JWTUtil.geneJsonWebToken(hsUserDetails);
        hsUserDetails.setToken(token);
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }

}
