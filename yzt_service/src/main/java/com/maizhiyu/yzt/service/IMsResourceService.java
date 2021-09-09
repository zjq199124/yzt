package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.MsResource;

import java.util.List;
import java.util.Map;

public interface IMsResourceService {

    List<Map<String, Object>> getResourceList();

    List<MsResource> getRoleResourceList(Long roleId);

    List<Map<String, Object>> getUserResourceList(Long userId);
}
