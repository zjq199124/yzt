package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsResource;

import java.util.List;
import java.util.Map;

public interface IHsResourceService extends IService<HsResource> {

    List<Map<String, Object>> getResourceList();

    List<Map<String, Object>> getRoleResourceList(Long roleId);

    List<Map<String, Object>> getUserResourceList(Long userId);
}
