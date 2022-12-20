package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsSytechItem;

import java.util.List;

public interface ITsSytechItemService extends IService<TsSytechItem> {

    Integer addSytechItem(TsSytechItem sytech);

    Integer delSytechItem(Long id);

    Integer setSytechItem(TsSytechItem sytech);

    TsSytechItem getSytechItem(Long id);

    List<TsSytechItem> getSytechItemList(Long sytechId);

    IPage<TsSytechItem> getSytechItemList(Long examinationPaperId, Long pageNum, Long pageSize);
}
