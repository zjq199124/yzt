package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsUser;

import java.util.List;
import java.util.Map;

public interface IPsUserService extends IService<PsUser> {

    Integer addUser(PsUser user);

    Integer setUser(PsUser user);

    Integer setUserByOpenid(PsUser user);

    PsUser getUser(Long id);

    PsUser getUserByOpenid(String openid);

}
