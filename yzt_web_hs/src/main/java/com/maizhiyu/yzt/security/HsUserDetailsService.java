package com.maizhiyu.yzt.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.HsUserRole;
import com.maizhiyu.yzt.mapper.HsUserRoleMapper;
import com.maizhiyu.yzt.serviceimpl.HsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class HsUserDetailsService implements UserDetailsService {

    @Autowired
    private HsUserDetailsMapper mapper;

    @Resource
    private HsUserService hsUserService;

    @Resource
    private HsUserRoleMapper hsUserRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询条件（根据用户名查询）
        QueryWrapper<HsUserDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);

        // 查询用户
        HsUserDetails user = mapper.selectOne(wrapper);

        // 用户存在
        if (user != null) {
            LambdaQueryWrapper<HsUserRole> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(HsUserRole::getUserId , user.getId());
            if (hsUserRoleMapper.selectOne(wrapper1) == null){
                HsUserRole hsUserRole = new HsUserRole();
                hsUserRole.setUserId(user.getId()).setRoleId(1L);
                hsUserRoleMapper.insert(hsUserRole);
            };
            return user;
        }
        // 用户不在
        else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
