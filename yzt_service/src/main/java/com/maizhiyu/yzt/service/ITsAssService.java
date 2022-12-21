package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsAss;

import java.util.List;
import java.util.Map;

public interface ITsAssService extends IService<TsAss> {



    public List<Map<String,Object>> getAssItem(Long id);

    public IPage<TsAss> getAsslist(Page page);

    public List<TsAss> getAssBytherapistId(Long therapistId);


}
