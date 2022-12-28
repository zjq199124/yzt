package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.aci.HsUserCI;
import com.maizhiyu.yzt.aro.HsUserRO;
import com.maizhiyu.yzt.avo.HsUserVO;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class HsUserController {

    @Autowired
    private IHsUserService service;


    @ApiOperation(value = "增加用户", notes = "增加用户")
    @PostMapping("/addUser")
    public Result<Long> addUser(HttpServletRequest request, @RequestBody @Valid HsUserRO.AddUserRO ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 数据转换
        HsUser user = HsUserCI.INSTANCE.convert(ro);
        user.setCustomerId(customerId);
        user.setStatus(1);
        user.setIsDoctor(1);
        // 查询医生是否存在
        HsUser oldUser = service.getUserByHisId(customerId, Long.valueOf(ro.getHisId()));
        // 不存在则插入
        if (oldUser == null) {
            // 密码为空时使用默认密码123456
            String password;
            if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
                password = "123456";
            } else {
                password = user.getPassword();
            }
            // 密码加密处理
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            String encoded = bcryptPasswordEncoder.encode(password);
            // 补充数据
            Date date = new Date();
            user.setPassword(encoded);
            user.setCreateTime(date);
            user.setUpdateTime(date);
            // 角色列表(系统默认医生角色是3)
            List<Long> roleList = new ArrayList<>();
            roleList.add(3L);
            user.setRoleList(roleList);
            System.out.println("----------------------");
            System.out.println(user);
            // 添加医生
            service.addUser(user);
        }
        // 存在则更新
        else {
            // 密码处理
            if (user.getPassword() != null && user.getPassword().trim().length() > 0) {
                BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
                String encoded = bcryptPasswordEncoder.encode(user.getPassword().trim());
                user.setPassword(encoded);
            }
            // 补充数据
            user.setId(oldUser.getId());
            user.setUpdateTime(new Date());
            service.setUser(user);
        }
        // 获取最新数据
        HsUser newUser = service.getUserByHisId(customerId, Long.valueOf(ro.getHisId()));
        HsUserVO.AddUserVO vo = HsUserCI.INSTANCE.invertAddUserVO(newUser);
        // 返回结果
        return Result.success(vo.getId());
    }

}
