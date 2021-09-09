package com.maizhiyu.yzt.service;

import java.util.List;
import java.util.Map;

public interface IHsResourceService {

    List<Map<String, Object>> getResourceList();

    List<Map<String, Object>> getRoleResourceList(Long roleId);

    List<Map<String, Object>> getUserResourceList(Long userId);
}
