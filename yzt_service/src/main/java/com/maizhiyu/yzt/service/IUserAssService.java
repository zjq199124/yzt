package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsAssOperationDetail;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.vo.TssAssVO;

import java.util.List;
import java.util.Map;

public interface IUserAssService extends IService<UserAss> {

    IPage<UserAss> getAssGrade(Page page, UserAss userAss);

    List<TsAssOperationDetail> getUserGrade(Long assId);
}
