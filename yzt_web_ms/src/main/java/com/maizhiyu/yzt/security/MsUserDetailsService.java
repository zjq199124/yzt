package com.maizhiyu.yzt.security;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.mapper.MsUserMapper;
import com.maizhiyu.yzt.service.IMsResourceService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MsUserDetailsService implements UserDetailsService {

    @Autowired
    private MsUserDetailsMapper mapper;

    @Autowired
    private MsUserMapper msUserMapper;
    @Autowired
    private IMsResourceService service;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 查询条件（根据用户名查询）
        QueryWrapper<MsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        // 查询用户
        MsUser msUser = msUserMapper.selectOne(wrapper);
//        //放入对象
//        MsUserRo msUserRo = new MsUserRo();
//        BeanUtils.copyProperties(msUser,msUserRo);
        // 用户存在
        if (msUser != null) {
            //查询用户的权限
            List<String> authorities = service.getUserPerms(msUser.getId());
//            msUser.setPermsList(authorities);
            //将权限放入user对象
            return new MsUserDetails(msUser,authorities);
        }
        // 用户不在
        else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
