package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.mapper.PsUserMapper;
import com.maizhiyu.yzt.service.IPsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class PsUserService implements IPsUserService {

    @Autowired
    private PsUserMapper mapper;

    @Override
    public Integer addUser(PsUser user) {
        Integer res;
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", user.getOpenid());
        Integer count = mapper.selectCount(wrapper);
        if (count > 0) {
            res = mapper.updateById(user);
        } else {
            res = mapper.insert(user);
        }
        return res;
    }

    @Override
    public Integer setUser(PsUser user) {
        return mapper.updateById(user);
    }

    @Override
    public Integer setUserByOpenid(PsUser user) {
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", user.getOpenid());
        return mapper.update(user, wrapper);
    }

    @Override
    public PsUser getUser(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public PsUser getUserByOpenid(String openid) {
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return mapper.selectOne(wrapper);
    }
}
