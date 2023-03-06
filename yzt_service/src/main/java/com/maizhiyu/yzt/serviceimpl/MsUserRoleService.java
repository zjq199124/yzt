package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.entity.MsUserDepartment;
import com.maizhiyu.yzt.entity.MsUserRole;
import com.maizhiyu.yzt.mapper.MsUserDepartmentMapper;
import com.maizhiyu.yzt.mapper.MsUserMapper;
import com.maizhiyu.yzt.mapper.MsUserRoleMapper;
import com.maizhiyu.yzt.service.IMsUserRoleService;
import com.maizhiyu.yzt.service.IMsUserService;
import com.maizhiyu.yzt.utils.ExistCheck;
import com.maizhiyu.yzt.vo.MsUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsUserRoleService extends ServiceImpl<MsUserRoleMapper,MsUserRole> implements IMsUserRoleService {

}
