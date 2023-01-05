package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.lang.Assert;
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
    public TssAssVO.OperationGradeVO getUserGrade(Long assId) {
        //查询考核数据
        TsAss tsAss = tsAssService.getById(assId);
        Assert.notNull(tsAss, "考核不存在!");

        //结果接收
        TssAssVO.OperationGradeVO operationGradeVO = new TssAssVO.OperationGradeVO();
        operationGradeVO.setAssId(tsAss.getId());

        //以考核id 查寻 考核项
        LambdaQueryWrapper<TsAssOperation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TsAssOperation::getSytechId, tsAss.getSytechId());
        List<TsAssOperation> operations = tsAssOperationService.list(wrapper);

        List<Long> operationIds = operations.stream().map(TsAssOperation::getId).collect(Collectors.toList());
        //已考核项id in 考核项详情 ts_ass_operation_detail
        LambdaQueryWrapper<TsAssOperationDetail> wrapper1 = Wrappers.lambdaQuery();
        wrapper1.in(TsAssOperationDetail::getOperationId, operationIds);
        List<TsAssOperationDetail> operationDetails = tsAssOperationDetailService.list(wrapper1);
        Set<Long> detailIds = operationDetails.stream().map(TsAssOperationDetail::getId).collect(Collectors.toSet());

        //查询用户考核结果
        LambdaQueryWrapper<UserAss> wrapper2 = Wrappers.lambdaQuery();
        wrapper2.eq(UserAss::getAssId, tsAss.getId());
        wrapper2.in(UserAss::getDetailId, detailIds);
        List<UserAss> userAsses = list(wrapper2);
        Assert.notNull(userAsses, "用户考核成绩不存在!");

        //返回结果封装
        List<TssAssVO.OperationDetail> operationDetailList = new ArrayList<>();
        //循环考核类别
        operations.stream().forEach(item -> {
            //获取用户考核项的考核结果
            List<TssAssVO.UserGrade> gradeList = new ArrayList<>();
            //当前考核类别下的的考核项列表
            List<Long> list = operationDetails.stream().filter(e -> e.getOperationId().equals(item.getId())).map(TsAssOperationDetail::getId).collect(Collectors.toList());
            //当前考核项对应的人员考核成绩
            List<UserAss> userAsseGrade = userAsses.stream().filter(e -> list.contains(e.getDetailId())).collect(Collectors.toList());
            //人员考核项考核成绩值填充
            userAsseGrade.stream().forEach(s -> {
                TssAssVO.UserGrade grade = new TssAssVO.UserGrade();
                grade.setDeduct(s.getDeduct());
                grade.setGetScore(s.getScore());
                Optional<TsAssOperationDetail> cc = operationDetails.stream().filter(e -> e.getId().equals(s.getDetailId())).findFirst();
                grade.setOperationDetails(cc.get().getDetail());
                gradeList.add(grade);
            });
            //填充考核项数据
            TssAssVO.OperationDetail operationDetail = new TssAssVO.OperationDetail();
            operationDetail.setOperationId(item.getId());
            operationDetail.setOperationName(item.getOperationName());
            operationDetail.setGetOperationScore(item.getScore());
            operationDetail.setOperationScore(8);
            operationDetail.setUserGrades(gradeList);
            operationDetailList.add(operationDetail);

        });
        //填充考核数据
        operationGradeVO.setOperationDetails(operationDetailList);
        return operationGradeVO;
    }
}


