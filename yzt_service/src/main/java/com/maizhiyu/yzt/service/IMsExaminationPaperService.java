package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsExaminationPaper;

import java.util.List;

/**
* className:MsExaminationPaper
* Package:com.maizhiyu.yzt.service
* Description:
* @DATE:2021/12/10 4:13 下午
* @Author:2101825180@qq.com
*/
public interface IMsExaminationPaperService extends IService<MsExaminationPaper> {
    Integer addMsExaminationPaper(MsExaminationPaper item);

    Integer delMsExaminationPaper(Long id);

    Integer setMsExaminationPaper(MsExaminationPaper item);

    MsExaminationPaper getMsExaminationPaper(Long id);

    IPage<MsExaminationPaper> getMsExaminationPaperList(Long sytechId, Integer pageNum, Integer pageSize);

    List<MsExaminationPaper> getMsExaminationPaperListBySytechId(Long sytechId);
}
