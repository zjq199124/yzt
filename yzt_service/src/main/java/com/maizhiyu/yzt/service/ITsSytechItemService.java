package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TsSytechItem;

import java.util.List;

public interface ITsSytechItemService {

    Integer addSytechItem(TsSytechItem sytech);

    Integer delSytechItem(Long id);

    Integer setSytechItem(TsSytechItem sytech);

    TsSytechItem getSytechItem(Long id);

    List<TsSytechItem> getSytechItemList(Long sytechId);
}
