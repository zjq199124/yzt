package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsAss;

import java.util.List;
import java.util.Map;

public interface ITsAssService extends IService<TsAss> {

    public List<Map<String,Object>> getAsslist(Long therapistId, Long sytechId, String createTime, String endTime, String term);

}
