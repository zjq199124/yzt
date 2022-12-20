package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.HsRole;
import com.maizhiyu.yzt.entity.HsRoleResource;
import com.maizhiyu.yzt.mapper.HsRoleMapper;
import com.maizhiyu.yzt.mapper.HsRoleResourceMapper;
import com.maizhiyu.yzt.service.IHsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class HsRoleService extends ServiceImpl<HsRoleMapper,HsRole> implements IHsRoleService {

    @Autowired
    private HsRoleMapper roleMapper;

    @Autowired
    private HsRoleResourceMapper roleResourceMapper;

    @Override
    public Integer addRole(HsRole role) {
        // 添加角色信息
        role.setStatus(1);
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        Integer res = roleMapper.insert(role);
        // 添加关系表数据
        for (Long resourceId : role.getResourceList()) {
            HsRoleResource roleResource = new HsRoleResource();
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
        QueryWrapper<HsRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        roleResourceMapper.delete(wrapper);
        // 返回结果
        return res;
    }

    @Override
    public Integer setRole(HsRole role) {
        // 更新角色信息
        Integer res = roleMapper.updateById(role);
        // 删除关系数据
        QueryWrapper<HsRoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", role.getId());
        roleResourceMapper.delete(wrapper);
        // 添加关系数据
        for (Long resourceId : role.getResourceList()) {
            HsRoleResource roleResource = new HsRoleResource();
            roleResource.setRoleId(role.getId());
            roleResource.setResourceId(resourceId);
            roleResourceMapper.insert(roleResource);
        }
        // 返回结果
        return res;
    }

    @Override
    public Integer setRoleStatus(HsRole role) {
        return roleMapper.updateById(role);
    }

    @Override
    public HsRole getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getRoleList(Page page, Long customerId, Integer status, String term) {
        IPage<Map<String, Object>> list = roleMapper.selectRoleList(page,customerId, status, term);
        return list;
    }
}
