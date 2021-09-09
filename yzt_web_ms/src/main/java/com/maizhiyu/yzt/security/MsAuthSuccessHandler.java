package com.maizhiyu.yzt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class MsAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                throws IOException, ServletException {

//        super.onAuthenticationSuccess(request, response, authentication);
        System.out.println("登录成功。。。。。。");

//        String accessControlAllowOrigin = request.getHeader("Access-Control-Allow-Origin");
//
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//        response.addHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);

        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(authentication));

//        // logger.info("authenticationΩuthentication success, {} login successfully", request.getParameter("username"));
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.sendRedirect("/home.html");
    }

}
