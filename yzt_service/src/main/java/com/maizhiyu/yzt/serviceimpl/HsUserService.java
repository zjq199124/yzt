package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.entity.HsUserDepartment;
import com.maizhiyu.yzt.entity.HsUserRole;
import com.maizhiyu.yzt.mapper.HsUserDepartmentMapper;
import com.maizhiyu.yzt.mapper.HsUserMapper;
import com.maizhiyu.yzt.mapper.HsUserRoleMapper;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class HsUserService extends ServiceImpl<HsUserMapper,HsUser> implements IHsUserService {

    @Autowired
    private HsUserMapper userMapper;

    @Autowired
    private HsUserRoleMapper userRoleMapper;

    @Autowired
    private HsUserDepartmentMapper userDepartmentMapper;

    @Override
    @ExistCheck(clazz = HsUser.class, fname = "username", message = "用户已存在")
    public Integer addUser(HsUser user) {
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
    public Integer setUser(HsUser user) {
        // 更新用户信息
        Integer res = userMapper.updateById(user);
        // 角色信息不空时才处理
        if (user.getRoleList() != null && user.getRoleList().size() > 0) {
            // 删除角色关系数据
            deleteUserRoleData(user.getId());
            // 添加角色信息
            insertUserRoleData(user.getId(), user.getRoleList());
        }
        // 部门信息不空时才处理
        if (user.getDepartmentList() != null && user.getDepartmentList().size() > 0) {
            // 删除部门关系数据
            deleteUserDepartmentData(user.getId());
            // 添加部门信息
            insertUserDepartmentData(user.getId(), user.getDepartmentList());
        }
        // 返回结果
        return res;
    }

    @Override
    public Integer setUserBasic(HsUser user) {
        return userMapper.updateById(user);
    }

    @Override
    public Integer setUserStatus(HsUser user) {
        return userMapper.updateById(user);
    }

    @Override
    public Map<String, Object> getUser(Long id) {
        return userMapper.selectUser(id);
    }

    public HsUser getUserByHisId(Long customerId, Long hisId) {
        QueryWrapper<HsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId)
                .eq("his_id", hisId)
                .orderByDesc("update_time")
                .last("limit 1");
        return userMapper.selectOne(wrapper);
    }



    @Override
    public HsUser getAdmin(Long customerId) {
        return userMapper.selectAdmin(customerId);
    }

    @Override
    public Integer setAdmin(HsUser user) {
        return userMapper.updateById(user);
    }

    @Override
    public List<Map<String, Object>> getUserList(Long customerId, Long departmentId, Long roleId, Integer status, String term) {
        List<Map<String, Object>> list = userMapper.selectUserList(customerId, departmentId, roleId, status, term, null, null);
        return list;
    }

    @Override
    public List<Map<String, Object>> getDoctorList(Long customerId, Long departmentId, Integer status, String term) {
        List<Map<String, Object>> list = userMapper.selectUserList(customerId, departmentId, null, status, term, 1, null);
        return list;
    }

    @Override
    public List<Map<String, Object>> getTherapistList(Long customerId, Long departmentId, Integer status, String term) {
        List<Map<String, Object>> list = userMapper.selectUserList(customerId, departmentId, null, status, term, null, 1);
        return list;
    }

    private void insertUserRoleData(Long userId, List<Long> roleList) {
        if (roleList != null) {
            for (Long roleId : roleList) {
                HsUserRole userRole = new HsUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                System.out.println(userRole);
                userRoleMapper.insert(userRole);
            }
        }
    }

    private void insertUserDepartmentData(Long userId, List<Long> departmentList) {
        if (departmentList != null) {
            for (Long departmentId : departmentList) {
                HsUserDepartment userDepartment = new HsUserDepartment();
                userDepartment.setUserId(userId);
                userDepartment.setDepartmentId(departmentId);
                userDepartmentMapper.insert(userDepartment);
            }
        }
    }

    private void deleteUserRoleData(Long userId) {
        QueryWrapper<HsUserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", userId);
        userRoleMapper.delete(userRoleWrapper);
    }

    private void deleteUserDepartmentData(Long userId) {
        QueryWrapper<HsUserDepartment> userDepartmentWrapper = new QueryWrapper<>();
        userDepartmentWrapper.eq("user_id", userId);
        userDepartmentMapper.delete(userDepartmentWrapper);
    }
}
