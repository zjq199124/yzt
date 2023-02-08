package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsFmaily;
import com.maizhiyu.yzt.mapper.PsFamilyMapper;
import com.maizhiyu.yzt.service.IPsFamilyService;
import org.springframework.stereotype.Service;

@Service
public class PsFamilyService extends ServiceImpl<PsFamilyMapper,PsFmaily> implements IPsFamilyService {

}
