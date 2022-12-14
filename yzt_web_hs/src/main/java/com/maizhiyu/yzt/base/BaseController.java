package com.maizhiyu.yzt.base;

import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * className:FormerController
 * Package:com.maizhiyu.yzt.controller
 * Description:
 *
 * @DATE:2021/12/16 3:34 下午
 * @Author:2101825180@qq.com
 */
@Controller
public class BaseController {

    @Autowired
    protected HttpServletRequest request;


    public Claims getClaims() {
        String token = request.getHeader("token") == null ? request.getParameter("token") : request.getHeader("token");

        if (token == null) {
            throw new BusinessException("token 不能为空");
        }

        Claims claims = JWTUtil.checkJWT(token);

        if (token == null) {
            throw new BusinessException("token 验证失败");
        }

        return claims;
    }

}
