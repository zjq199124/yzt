package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TeModel;
import com.maizhiyu.yzt.entity.TeTrouble;

import java.util.List;
import java.util.Map;

public interface ITeTroubleService {

    Integer addTrouble(TeTrouble trouble);

    Integer delTrouble(Long id);

    Integer setTrouble(TeTrouble trouble);

    TeTrouble getTrouble(Long id);

    List<Map<String,Object>> getTroubleList(Integer etype, Integer status);
}
