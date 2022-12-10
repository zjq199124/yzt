package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsExaminationPaper;
import com.maizhiyu.yzt.entity.TsSytechItem;

import java.util.List;

public interface ITsSytechItemService extends IService<TsSytechItem> {

    Integer addSytechItem(TsSytechItem sytech);

    Integer delSytechItem(Long id);

    Integer setSytechItem(TsSytechItem sytech);

    TsSytechItem getSytechItem(Long id);

    List<TsSytechItem> getSytechItemList(Long sytechId);

    PageInfo<TsSytechItem> getSytechItemList(Long examinationPaperId, Integer pageNum, Integer pageSize);
}
