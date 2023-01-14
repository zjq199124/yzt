package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.ro.BatchAddUserRO;

import java.util.List;
import java.util.Map;

public interface ITsAssService extends IService<TsAss> {



    public List<Map<String,Object>> getAssItem(Long id);

    IPage<Map<String, Object>> getAsslist(Page page,String phoneOrtherapistName,Long examinerId,
                                        Long sytechId ,Integer status);

    List<TsAss> addBatch(BatchAddUserRO batchAddUserRO);



}
