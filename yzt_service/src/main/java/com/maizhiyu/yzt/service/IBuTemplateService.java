package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuTemplate;

public interface IBuTemplateService extends IService<BuTemplate> {

    Integer addTemplate(BuTemplate template);

    Integer delTemplate(Long id);

    Integer setTemplate(BuTemplate template);

    BuTemplate getTemplate(Long id);

    IPage<BuTemplate> getTemplateList(Page page, Long doctocId);

}
