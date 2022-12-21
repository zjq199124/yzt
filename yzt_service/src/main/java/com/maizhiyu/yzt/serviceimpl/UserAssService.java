package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.mapper.UserAssMapper;
import com.maizhiyu.yzt.service.IUserAssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAssService extends ServiceImpl<UserAssMapper, UserAss> implements IUserAssService {

}
