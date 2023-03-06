package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsResource;

import java.util.List;
import java.util.Map;

public interface IMsResourceService extends IService<MsResource> {

    List<Map<String, Object>> getResourceList();

    List<MsResource> getRoleResourceList(Long roleId);

    List<Map<String, Object>> getUserResourceList(Long userId);

    List<String> getUserPerms(Long userId);
}
