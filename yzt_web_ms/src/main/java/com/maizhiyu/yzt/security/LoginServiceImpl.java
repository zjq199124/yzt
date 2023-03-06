package com.maizhiyu.yzt.security;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.MsUserMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.result.ResultCode;
import com.maizhiyu.yzt.util.JwtUtil;
import com.maizhiyu.yzt.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author zjq
 * @date 2023-03-02
 */
@Service
public class LoginServiceImpl implements LoginServcie{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MsUserDetailsMapper mapper;
    @Autowired
    private MsUserMapper msUserMapper;
    @Override
    public Result login(MsUser user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        if(msUserMapper.selectOne(new QueryWrapper<MsUser>().eq("user_name",user.getUserName())).getStatus()==0){
            return Result.failure("该用户账户已停用");
        }
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            return  Result.failure("登陆失败");
        }
        //使用userid生成token
        MsUserDetails loginUser = (MsUserDetails) authenticate.getPrincipal();
//        User loginUser = (User) authenticate.getPrincipal();
//        // 查询条件（根据用户名查询）
//        QueryWrapper<MsUser> wrapper = new QueryWrapper<>();
//        wrapper.eq("username", loginUser.getUsername());
//        // 查询用户
//        MsUser msUser = msUserMapper.selectOne(wrapper);

        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(loginUser);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,jsonObject);
        //把token响应给前端
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",userId);
        map.put("token",jwt);
        map.put("perms",loginUser.getAuthorities());
        return new Result(ResultCode.SUCCESS.code(), "登陆成功",map);
    }

    @Override
    public Result logout() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取token
        String token = request.getHeader("token");
        //解析token
        Long userid;
        Claims claims = JwtUtil.parseJWT(token);
        userid = Long.valueOf(claims.getSubject());
        redisCache.deleteObject("login:"+userid);
        return new Result(200,"退出成功",null);
    }

}
