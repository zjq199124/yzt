package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.constants.DictConstant;
import com.maizhiyu.yzt.entity.DictCommon;
import com.maizhiyu.yzt.entity.TeTrouble;
import com.maizhiyu.yzt.mapper.DictCommonMapper;
import com.maizhiyu.yzt.mapper.TeTroubleMapper;
import com.maizhiyu.yzt.service.ITeTroubleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeTroubleService implements ITeTroubleService {

    @Autowired
    private TeTroubleMapper troubleMapper;

    @Override
    public TeTrouble getTrouble(Long id) {
        return troubleMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getTroubleList(Integer etype, Integer status) {
        // 查询出所有数据
        QueryWrapper<TeTrouble> troubleWrapper = new QueryWrapper<>();
        troubleWrapper.select("id", "status", "level", "parent", "etype", "title");
        if (status != null) {
            troubleWrapper.eq("status", status);
        }
        if (etype != null) {
            troubleWrapper.like("etype", etype);
        }
        List<Map<String, Object>> all = troubleMapper.selectMaps(troubleWrapper);

        // 筛选出一级数据
        List<Map<String, Object>> levelOne = new ArrayList<>();
        for (Map<String, Object> map : all) {
            if ((Integer) map.get("level") == 1) {
                map.put("children", new ArrayList<Map<String, Object>>());
                levelOne.add(map);
            }
        }

        // 筛选出二级数据
        List<Map<String, Object>> levelTwo = new ArrayList<>();
        for (Map<String, Object> map : all) {
            if ((Integer) map.get("level") == 2) {
                levelTwo.add(map);
            }
        }

        // 整理成树形结构
        for (Map<String, Object> lone : levelOne) {
            Long id = (Long) lone.get("id");
            for (Map<String, Object> ltwo : levelTwo) {
                Long pid = (Long) ltwo.get("parent");
                if (pid == id) {
                    ((ArrayList<Map<String, Object>>) lone.get("children")).add(ltwo);
                }
            }
        }

        //返回结果
        return levelOne;
    }
}
