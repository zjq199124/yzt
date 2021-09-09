package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.MsResource;
import com.maizhiyu.yzt.entity.MsRole;
import com.maizhiyu.yzt.entity.MsRoleResource;
import com.maizhiyu.yzt.mapper.MsResourceMapper;
import com.maizhiyu.yzt.mapper.MsRoleMapper;
import com.maizhiyu.yzt.mapper.MsRoleResourceMapper;
import com.maizhiyu.yzt.service.IMsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsRoleService implements IMsRoleService {

    @Autowired
    private MsRoleMapper roleMapper;

    @Autowired
    private MsRoleResourceMapper roleResourceMapper;

    @Override
    public Integer addRole(MsRole role) {
        // 添加角色信息
        role.setStatus(1);
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        Integer res = roleMapper.insert(role);
        // 添加关系表数据
        for (Long resourceId : role.getResourceList()) {
            MsRoleResource roleResource = new MsRoleResource();
            roleResource.setRoleId(role.getId());
            roleResource.setResourceId(resourceId);
            roleResourceMapper.insert(roleResource);
        }
        // 返回结果
        return res;
    }

    @Override
    public Integer delRole(Long id) {
        // 删除角色信息
        Integer res = roleMapper.deleteById(id);
        // 删除关系表数
        QueryWrapper<MsRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        roleResourceMapper.delete(wrapper);
        // 返回结果
        return res;
    }

    @Override
    public Integer setRole(MsRole role) {
        // 更新角色信息
        Integer res = roleMapper.updateById(role);
        // 删除关系数据
        QueryWrapper<MsRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", role.getId());
        roleResourceMapper.delete(wrapper);
        // 添加关系数据
        for (Long resourceId : role.getResourceList()) {
            MsRoleResource roleResource = new MsRoleResource();
            roleResource.setRoleId(role.getId());
            roleResource.setResourceId(resourceId);
            roleResourceMapper.insert(roleResource);
        }
        // 返回结果
        return res;
    }

    @Override
    public Integer setRoleStatus(MsRole role) {
        return roleMapper.updateById(role);
    }

    @Override
    public MsRole getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getRoleList(Integer status, String term) {
        List<Map<String, Object>> list = roleMapper.selectRoleList(status, term);
        return list;
    }
}
