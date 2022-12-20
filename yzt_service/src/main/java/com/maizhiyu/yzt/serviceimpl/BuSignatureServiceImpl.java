package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuSignature;
import com.maizhiyu.yzt.mapper.BuSignatureMapper;
import com.maizhiyu.yzt.service.BuSignatureService;
import org.springframework.stereotype.Service;
 
/**
 * 治疗签到表(BuSignature)表服务实现类
 *
 * @author liuyzh
 * @since 2022-12-19 19:05:36
 */
@Service
public class BuSignatureServiceImpl extends ServiceImpl<BuSignatureMapper, BuSignature> implements BuSignatureService {
 
}
