package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsUserDepartment;
import com.maizhiyu.yzt.entity.MsUserRole;
import com.maizhiyu.yzt.mapper.MsUserDepartmentMapper;
import com.maizhiyu.yzt.mapper.MsUserMapper;
import com.maizhiyu.yzt.mapper.MsUserRoleMapper;
import com.maizhiyu.yzt.service.IMsUserDepartmentService;
import com.maizhiyu.yzt.service.IMsUserRoleService;
import com.maizhiyu.yzt.service.IMsUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsUserDepartmentService extends ServiceImpl<MsUserDepartmentMapper, MsUserDepartment> implements IMsUserDepartmentService {

}
