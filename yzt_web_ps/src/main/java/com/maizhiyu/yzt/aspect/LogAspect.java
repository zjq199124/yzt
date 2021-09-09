package com.maizhiyu.yzt.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.maizhiyu.yzt.controller..*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 基础信息
        String url = request.getRequestURI();
        String method = request.getMethod();
        String function = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        // 参数信息
        StringBuilder parameters = new StringBuilder();
        Enumeration<String> paramter = request.getParameterNames();
        while (paramter.hasMoreElements()) {
            String key = (String) paramter.nextElement();
            String val = request.getParameter(key);
            parameters.append(key + ":" + val + ", ");
        }

        // 输出日志
        String content = String.format(" [%s] %s - %s | %s", method, url, function, parameters.toString());
        logger.info(content);
    }

}

