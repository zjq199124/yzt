package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsUser;

import java.util.List;
import java.util.Map;

public interface IHsUserService extends IService<HsUser> {

    Integer addUser(HsUser user);

    Integer delUser(Long id);

    Integer setUser(HsUser user);

    Integer setUserBasic(HsUser user);

    Integer setUserStatus(HsUser user);

    Map<String, Object> getUser(Long id);

    HsUser getUserByHisId(Long customerId, Long hisId);

    HsUser getAdmin(Long customerId);

    Integer setAdmin(HsUser user);

    IPage<Map<String, Object>> getUserList(Page page, Long customerId, Long departmentId, Long roleId, Integer status, String term);

    IPage<Map<String, Object>> getDoctorList(Page page,Long customerId, Long departmentId, Integer status, String term);

    IPage<Map<String, Object>> getTherapistList(Page page,Long customerId, Long departmentId, Integer status, String term);

    IPage<HsUser> getTherapist(Page page,Long isTherapist);
}
