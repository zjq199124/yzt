package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.MsDepartment;
import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.entity.MsUserDepartment;
import com.maizhiyu.yzt.entity.MsUserRole;
import com.maizhiyu.yzt.mapper.MsUserDepartmentMapper;
import com.maizhiyu.yzt.mapper.MsUserMapper;
import com.maizhiyu.yzt.mapper.MsUserRoleMapper;
import com.maizhiyu.yzt.service.IMsUserService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsUserService implements IMsUserService {

    @Autowired
    private MsUserMapper userMapper;

    @Autowired
    private MsUserRoleMapper userRoleMapper;

    @Autowired
    private MsUserDepartmentMapper userDepartmentMapper;

    @Override
    @ExistCheck(clazz = MsUser.class, fname = "username", message = "用户已存在")
    public Integer addUser(MsUser user) {
        // 添加用户信息
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        Integer res = userMapper.insert(user);
        // 添加角色信息
        insertUserRoleData(user.getId(), user.getRoleList());
        // 添加部门信息
        insertUserDepartmentData(user.getId(), user.getDepartmentList());
        // 返回结果
        return res;
    }

    @Override
    public Integer delUser(Long id) {
        // 删除用户信息
        Integer res = userMapper.deleteById(id);
        // 删除角色关系数据
        deleteUserRoleData(id);
        // 删除部门关系数据
        deleteUserDepartmentData(id);
        // 返回结果
        return res;
    }

    @Override
    public Integer setUser(MsUser user) {
        // 更新用户信息
        Integer res = userMapper.updateById(user);
        // 删除角色关系数据
        deleteUserRoleData(user.getId());
        // 删除部门关系数据
        deleteUserDepartmentData(user.getId());
        // 添加角色信息
        insertUserRoleData(user.getId(), user.getRoleList());
        // 添加部门信息
        insertUserDepartmentData(user.getId(), user.getDepartmentList());
        // 返回结果
        return res;
    }

    @Override
    public Integer setUserBasic(MsUser user) {
        return userMapper.updateById(user);
    }

    @Override
    public MsUser getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getUserList(Long departmentId, Long roleId, Integer status, String term) {
        List<Map<String, Object>> list = userMapper.selectUserList(departmentId, roleId, status, term);
        return list;
    }

    private void insertUserRoleData(Long userId, List<Long> roleList) {
        for (Long roleId : roleList) {
            MsUserRole userRole = new MsUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    private void insertUserDepartmentData(Long userId, List<Long> departmentList) {
        for (Long departmentId : departmentList) {
            MsUserDepartment userDepartment = new MsUserDepartment();
            userDepartment.setUserId(userId);
            userDepartment.setDepartmentId(departmentId);
            userDepartmentMapper.insert(userDepartment);
        }
    }

    private void deleteUserRoleData(Long userId) {
        QueryWrapper<MsUserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", userId);
        userRoleMapper.delete(userRoleWrapper);
    }

    private void deleteUserDepartmentData(Long userId) {
        QueryWrapper<MsUserDepartment> userDepartmentWrapper = new QueryWrapper<>();
        userDepartmentWrapper.eq("user_id", userId);
        userDepartmentMapper.delete(userDepartmentWrapper);
    }
}
