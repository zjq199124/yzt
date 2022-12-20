package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsRole;
import com.maizhiyu.yzt.entity.MsRoleResource;
import com.maizhiyu.yzt.mapper.MsRoleMapper;
import com.maizhiyu.yzt.mapper.MsRoleResourceMapper;
import com.maizhiyu.yzt.service.IMsRoleService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsRoleService extends ServiceImpl<MsRoleMapper,MsRole> implements IMsRoleService {

    @Autowired
    private MsRoleMapper roleMapper;

    @Autowired
    private MsRoleResourceMapper roleResourceMapper;

    @Override
    @ExistCheck(clazz = MsRole.class, fname = "rolename", message = "角色已存在")
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
    public IPage<Map<String, Object>> getRoleList(Page page, Integer status, String term) {
        IPage<Map<String, Object>> list = roleMapper.selectRoleList(page,status, term);
        return list;
    }
}
