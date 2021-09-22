package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.constants.DictConstant;
import com.maizhiyu.yzt.entity.DictCommon;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.DictCommonMapper;
import com.maizhiyu.yzt.service.IDictCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class DictCommonService implements IDictCommonService {

    @Autowired
    private DictCommonMapper mapper;

    @Override
    public Integer addCate(DictCommon cate) {
        cate.setCate("CATEGORY");
        return mapper.insert(cate);
    }

    @Override
    public Integer delCate(Long id) {
        QueryWrapper<DictCommon> wrapper = new QueryWrapper<>();
        wrapper.eq("parent", id);
        Integer cnt = mapper.selectCount(wrapper);
        if (cnt > 0) {
            throw new BusinessException("该类别下还有条目存在，无法删除！");
        }
        return mapper.deleteById(id);
    }

    @Override
    public Integer setCate(DictCommon cate) {
        return mapper.updateById(cate);
    }

    @Override
    public DictCommon getCate(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getCateList(Integer status, String term) {
        QueryWrapper<DictCommon> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        wrapper.eq("parent", "0");
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (term != null) {
            wrapper.like("content", term);
        }
        return mapper.selectMaps(wrapper);
    }

    @Override
    public Integer addItem(DictCommon item) {
        // 所属类别校验
        QueryWrapper<DictCommon> wrapper = new QueryWrapper<>();
        wrapper.eq("parent", 0);
        wrapper.eq("cate", item.getCate());
        DictCommon dict = mapper.selectOne(wrapper);
        if (dict == null) {
            throw new BusinessException("该条目所属类别不存在，无法添加！");
        }
        // 添加条目记录
        item.setParent(dict.getId());
        item.setStatus(1);
        return mapper.insert(item);
    }

    @Override
    public Integer delItem(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setItem(DictCommon item) {
        return mapper.updateById(item);
    }

    @Override
    public DictCommon getItem(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getItemList(String cate, Integer status, String term) {
        QueryWrapper<DictCommon> wrapper = new QueryWrapper<>();
        wrapper.ne("parent", 0);
        if (cate != null) {
            wrapper.eq("cate", cate);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (term != null) {
            wrapper.like("content", term);
        }
        return mapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getEquipTypeList() {
        return getDictItemList("CATE_EQUIP_TYPE");
    }

    @Override
    public List<Map<String, Object>> getMaintainTypeList() {
        return getDictItemList("CATE_MAINTAIN_TYPE");
    }

    @Override
    public List<Map<String, Object>> getSymptomTypeList() {
        return getDictItemList("CATE_SYMPTOM_TYPE");
    }


    private List<Map<String, Object>> getDictItemList(String cate) {
        QueryWrapper<DictCommon> wrapper = new QueryWrapper<>();
        wrapper.ne("parent", 0);
        wrapper.eq("cate", cate);
        return mapper.selectMaps(wrapper);
    }
}
