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

    @Resource
    private IHsUserService userService;


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

    public HsUserDetails getHsUserDetails() throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            HsUserDetails hsUserDetails = (HsUserDetails) authentication.getPrincipal();
            return hsUserDetails;
        } catch (Exception e) {
            throw new Exception("后台服务已重启，请退出重新登录!");
        }
    }

    public Long getCustomerId() throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            HsUserDetails hsUserDetails = (HsUserDetails) authentication.getPrincipal();
            Map<String, Object> userMap = userService.getUser(hsUserDetails.getId());
            Long customerId = Long.parseLong(userMap.get("customerId").toString());
            return customerId;
        } catch (Exception e) {
            throw new Exception("后台服务已重启，请退出重新登录!");
        }
    }
}
