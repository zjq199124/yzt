package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.HsResource;
import com.maizhiyu.yzt.entity.HsRoleResource;
import com.maizhiyu.yzt.mapper.HsResourceMapper;
import com.maizhiyu.yzt.mapper.HsRoleResourceMapper;
import com.maizhiyu.yzt.service.IHsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class HsResourceService extends ServiceImpl<HsResourceMapper,HsResource> implements IHsResourceService {

    @Autowired
    private HsResourceMapper mapper;

    @Resource
    private HsRoleResourceMapper hsRoleResourceMapper;

    @Override
    public List<Map<String, Object>> getResourceList() {
        QueryWrapper<HsResource> wrapper = new QueryWrapper<>();
//        wrapper.eq("type", 1);
        List<HsResource> list = mapper.selectList(wrapper);
        List<Map<String, Object>> result = formatResourceList(list);
        return result;
    }

    @Override
    public List<Map<String, Object>> getRoleResourceList(Long roleId) {
        List<HsResource> list = mapper.selectRoleResourceList(roleId);
        List<Map<String, Object>> result = formatResourceList(list);
        return result;
    }

    @Override
    public List<Map<String, Object>> getUserResourceList(Long userId) {
        List<HsResource> list = mapper.selectUserResourceList(userId);
        List<Map<String, Object>> result = formatResourceList(list);
        return result;
    }

    private List<Map<String, Object>> formatResourceList(List<HsResource> resources) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HsResource item : resources) {
            // 生成目录结构
            Map<String, Object> menu = (Map<String, Object>) JSON.toJSON(item);
            menu.put("children", new ArrayList<Object>());
            // 如果是父级节点
            if (item.getLevel() == 1) {
                list.add(menu);
            }
            // 如果是子级节点
            else {
                for (Map<String, Object> parent : list) {
                    if (parent.get("id") == item.getParent()) {
                        // 添加到父级节点的children列表中
                        ArrayList<Object> children = (ArrayList<Object>) parent.get("children");
                        children.add(menu);
                        // 设置父级目录默认打开第一个子目录
                        if (children.size() == 1) {
                            parent.put("redirect", item.getValue());
                        }
                    }
                }
            }
        }
        return list;
    }
}
