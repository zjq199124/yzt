package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuTemplate;

import java.util.List;
import java.util.Map;

public interface IBuTemplateService {

    Integer addTemplate(BuTemplate template);

    Integer delTemplate(Long id);

    Integer setTemplate(BuTemplate template);

    BuTemplate getTemplate(Long id);

    List<BuTemplate> getTemplateList(Long doctorId);

}
