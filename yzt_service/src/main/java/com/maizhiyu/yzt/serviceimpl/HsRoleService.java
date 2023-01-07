package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maizhiyu.yzt.entity.HsRole;
import com.maizhiyu.yzt.entity.HsRoleResource;
import com.maizhiyu.yzt.mapper.HsRoleMapper;
import com.maizhiyu.yzt.mapper.HsRoleResourceMapper;
import com.maizhiyu.yzt.service.IHsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class HsRoleService extends ServiceImpl<HsRoleMapper, HsRole> implements IHsRoleService {

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
    public Boolean setRole(HsRole role) {
        //当前更新的权限列表
        List<Long> resourceList = role.getResourceList();
        //旧的权限列表
        List<HsRoleResource> oldResources = roleResourceMapper.getByRoleId(role.getId());
        List<Long> oldResourceList = oldResources.stream().filter(e -> e.getIsDel() == 0).map(HsRoleResource::getResourceId).collect(Collectors.toList());

        //差集(删除)
        List<Long> removeList = new ArrayList<>(oldResourceList);
        removeList.removeAll(resourceList);
        //删除
        removeList.stream().forEach(e -> {
            // 删除关系数据
            LambdaQueryWrapper<HsRoleResource> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(HsRoleResource::getRoleId, role.getId());
            wrapper.eq(HsRoleResource::getResourceId, e);
            roleResourceMapper.delete(wrapper);
        });

        //差集(新增)
        List<Long> saveList = new ArrayList<>(resourceList);
        saveList.removeAll(oldResourceList);

        //删除的数据列表
        List<Long> removeResourceList = oldResources.stream().filter(e -> e.getIsDel() == 1).map(HsRoleResource::getResourceId).collect(Collectors.toList());

        //新增
        saveList.stream().forEach(e -> {
            //新增的数据是否在已删除的数据中，如果在则恢复数据
            if (removeResourceList.contains(e)) {
                Optional<Long> optional = oldResources.stream().filter(s -> s.getResourceId().equals(e)).map(HsRoleResource::getId).findFirst();
                roleResourceMapper.RecoverById(optional.get());
            } else {
                HsRoleResource roleResource = new HsRoleResource();
                roleResource.setRoleId(role.getId());
                roleResource.setResourceId(e);
                roleResourceMapper.insert(roleResource);
            }

        });
        // 更新角色信息
        return updateById(role);
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
        IPage<Map<String, Object>> list = roleMapper.selectRoleList(page, customerId, status, term);
        return list;
    }
}
