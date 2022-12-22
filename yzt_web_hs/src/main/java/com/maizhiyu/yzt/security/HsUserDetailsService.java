package com.maizhiyu.yzt.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class HsUserDetailsService implements UserDetailsService {

    @Autowired
    private HsUserDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询条件（根据用户名查询）
        QueryWrapper<HsUserDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);

        // 查询用户
        HsUserDetails user = mapper.selectOne(wrapper);

        // 用户存在
        if (user != null) {
            return user;
        }

        // 用户不在
        else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
