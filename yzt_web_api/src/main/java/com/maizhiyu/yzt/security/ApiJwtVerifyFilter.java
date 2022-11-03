package com.maizhiyu.yzt.security;

import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class ApiJwtVerifyFilter extends BasicAuthenticationFilter {

    public ApiJwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        // 获取header
        String header = request.getHeader(JwtTokenUtils.HEADER);

        // 处理header
        if (header != null && header.startsWith(JwtTokenUtils.PREFIX)) {

            // 截取token
            String token = header.substring(JwtTokenUtils.PREFIX.length());

            // 解析token
            Claims claims = JwtTokenUtils.parse(token);

            // 解析username
            Long id = new Long((Integer) claims.get("id"));
            String username = (String) claims.get("username");

            // 获取信息
            if (username != null) {

                // 获取context
                ServletContext servletContext = request.getServletContext();
                ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

                // 获取service
                ApiCustomerDetailsService detailsService = applicationContext.getBean(ApiCustomerDetailsService.class);

                // 查询信息
                UserDetails userDetails = detailsService.loadUserByUsername(username);

                // 设置Authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

}
