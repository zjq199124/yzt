package com.maizhiyu.yzt.config;

import com.maizhiyu.yzt.security.MsAuthFailureHandler;
import com.maizhiyu.yzt.security.MsAuthSuccessHandler;
import com.maizhiyu.yzt.security.MsLogoutSuccessHandler;
import com.maizhiyu.yzt.security.MsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MsAuthSuccessHandler successHandler;

    @Autowired
    private MsAuthFailureHandler failureHandler;

    @Autowired
    private MsLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private MsUserDetailsService userDetailsService;

    /** 配置相关设置 **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//
//                // 设置访问白名单（匹配这些url不需要认证）
//                .antMatchers("/**/*.css").permitAll()
//                .antMatchers("/**/*.js").permitAll()
//                .antMatchers("/**/docs.html").permitAll()
//                .antMatchers("/**/api-docs").permitAll()
//
//                // 其他的请求都需要进行认证
//                .anyRequest().authenticated()
//
//                // 登入设置
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll()
//
//                // 登出设置
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .permitAll()
//
//                // 跨域设置（允许跨域请求）
//                .and().cors()
//                .and().csrf().disable()
//
//                // entry point 设置
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

//                /* 这里是测试配置，放开所有访问限制 */
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and().logout().permitAll()
                // 允许跨域访问
                .and().cors()
                .and().csrf().disable()
                // entry point 设置
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    /** 配置认证用户来源 **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用内存中的测试数据
        // auth.inMemoryAuthentication()
        //        .withUser("admin")
        //        .password("{noop}123456")
        //        .roles("USER");

        // 使用数据库中的正式数据
        auth
                // 设置UserDetailService
                .userDetailsService(userDetailsService)
                // 设置密码加密类
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    class MyPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }
        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return s.equals(charSequence.toString());
        }
    }


    class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }


    public static void main(String[] args) {

        String password = "admin";

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

        String encoded = bcryptPasswordEncoder.encode(password);

        System.out.println("password: " + password);
        System.out.println("encoded: " + encoded);
    }
}

