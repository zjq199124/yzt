package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TeModel;
import com.maizhiyu.yzt.entity.TeTrouble;

import java.util.List;
import java.util.Map;

public interface ITeTroubleService {

    public TeTrouble getTrouble(Long id);

    public List<Map<String,Object>> getTroubleList(Integer etype, Integer status);
}
