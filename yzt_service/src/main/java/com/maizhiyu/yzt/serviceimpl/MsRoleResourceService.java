package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsRole;
import com.maizhiyu.yzt.entity.MsRoleResource;
import com.maizhiyu.yzt.entity.MsUserRole;
import com.maizhiyu.yzt.mapper.MsRoleMapper;
import com.maizhiyu.yzt.mapper.MsRoleResourceMapper;
import com.maizhiyu.yzt.mapper.MsUserRoleMapper;
import com.maizhiyu.yzt.service.IMsRoleResourceService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsRoleResourceService extends ServiceImpl<MsRoleResourceMapper,MsRoleResource> implements IMsRoleResourceService {

}
