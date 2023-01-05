package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.entity.TsAssOperation;
import com.maizhiyu.yzt.entity.TsAssOperationDetail;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.mapper.UserAssMapper;
import com.maizhiyu.yzt.service.IUserAssService;
import com.maizhiyu.yzt.vo.TssAssVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAssService extends ServiceImpl<UserAssMapper, UserAss> implements IUserAssService {

    @Resource
    private TsAssService tsAssService;
    @Resource
    private TsAssOperationService tsAssOperationService;

    @Resource
    private TsAssOperationDetailService tsAssOperationDetailService;


    @Override
    public IPage<UserAss> getAssGrade(Page page, UserAss userAss) {
        QueryWrapper<UserAss> wrapper = new QueryWrapper<UserAss>(userAss);
        wrapper.lambda().orderByAsc(UserAss::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<TsAssOperationDetail> getUserGrade(Long assId) {
        //查询考核数据
        TsAss tsAss=tsAssService.getById(assId);
        //以考核id 查寻 考核项
        LambdaQueryWrapper<TsAssOperation> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(TsAssOperation::getSytechId,tsAss.getSytechId());
        List<TsAssOperation> operations=tsAssOperationService.list(wrapper);

        List<Long> operationIds=operations.stream().map(TsAssOperation::getId).collect(Collectors.toList());
        //已考核项id in 考核项详情 ts_ass_operation_detail
        LambdaQueryWrapper<TsAssOperationDetail> wrapper1=Wrappers.lambdaQuery();
        wrapper1.in(TsAssOperationDetail::getOperationId,operationIds);
        List<TsAssOperationDetail> operationDetails = tsAssOperationDetailService.list(wrapper1);
        List<Long> detailIds=operationDetails.stream().map(TsAssOperationDetail::getId).collect(Collectors.toList());

        //查询用户考核结果
        LambdaQueryWrapper<UserAss> wrapper2=Wrappers.lambdaQuery();
        wrapper2.eq(UserAss::getAssId,tsAss.getId());
        wrapper2.in(UserAss::getDetailId,detailIds);
        List<UserAss> userAsses=list(wrapper2);


        return operationDetails;
    }
}
//        list.stream().forEach(e->{
//            String[] a=e..split("/");
//            String[] b=e.getGetScore().split(",");
//            List<String> collect = Arrays.stream(a).collect(Collectors.toList());
//            List<String> collect1 = Arrays.stream(b).collect(Collectors.toList());
//        });


