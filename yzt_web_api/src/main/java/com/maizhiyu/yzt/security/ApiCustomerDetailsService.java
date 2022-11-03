package com.maizhiyu.yzt.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ApiCustomerDetailsService implements UserDetailsService {

    @Autowired
    private ApiCustomerDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询条件（根据用户名查询）
        QueryWrapper<ApiCustomerDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);

        // 查询用户
        ApiCustomerDetails customer = mapper.selectOne(wrapper);

        // 用户存在
        if (customer != null) {
            return customer;
        }

        // 用户不在
        else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
