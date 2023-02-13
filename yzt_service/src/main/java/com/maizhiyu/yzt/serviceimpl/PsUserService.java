package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.mapper.PsUserMapper;
import com.maizhiyu.yzt.service.IPsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor=Exception.class)
public class PsUserService extends ServiceImpl<PsUserMapper,PsUser> implements IPsUserService {

    @Autowired
    private PsUserMapper mapper;

    @Override
    public Integer addUser(PsUser user) {
        Integer res;
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", user.getOpenid());
        Long count = mapper.selectCount(wrapper);
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
        wrapper.eq("openid", openid)
                .eq("is_del", 0)
                .orderByDesc("id")
                .last("limit 1");

        return mapper.selectOne(wrapper);
    }

    @Override
    public PsUser getUserByPhone(String phone) {
        LambdaQueryWrapper<PsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PsUser::getPhone, phone)
                .eq(PsUser::getIsDel, 0)
                .orderByDesc(PsUser::getCreateTime)
                .last("limit 1");

        return mapper.selectOne(wrapper);
    }
}
