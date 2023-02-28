package com.maizhiyu.yzt.base;

import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 从JWT token 中获取用户信息
     * @return
     */
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

    //security 会话中获取用户登录信息
    public HsUserDetails getHsUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object a = authentication.getPrincipal();
        //匿名用户
        Assert.isTrue(a.equals("anonymousUser"), "当前用户未登录，或登录已失效");
        HsUserDetails hsUserDetails = (HsUserDetails) authentication.getPrincipal();
        if (Objects.isNull(hsUserDetails)) {
            throw new BusinessException("认证信息获取失败");
        }
        return hsUserDetails;
    }

    //    当前登录用户id
    public Long getUserId() {
        return getHsUserDetails().getId();
    }

    /**
     * 获取当前会话客户id
     *
     * @return
     * @throws Exception
     */
    public Long getCustomerId() throws Exception {
        return getHsUserDetails().getCustomerId();
    }

    /**
     * 获取当前会话用户id
     *
     * @return
     * @throws Exception
     */
    public Long getUserId() throws Exception {
        return getHsUserDetails().getId();
    }

}
