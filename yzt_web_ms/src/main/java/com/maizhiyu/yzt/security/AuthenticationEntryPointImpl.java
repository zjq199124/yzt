package com.maizhiyu.yzt.security;

import com.alibaba.fastjson.JSON;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zjq
 * @date 2023-03-02
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录",null);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}