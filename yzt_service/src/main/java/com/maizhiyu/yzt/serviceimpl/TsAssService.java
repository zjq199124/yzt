package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.service.ITsAssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class TsAssService extends ServiceImpl<TsAssMapper, TsAss> implements ITsAssService {

    @Resource
    private TsAssMapper tsAssMapper;

    @Override
    public List<Map<String ,Object>> getAssItem(Long id){
        List<Map<String,Object>> list = tsAssMapper.selectAssItem(id);
        return list;
    }


    @Override
    public IPage<TsAss> getAsslist(Page page){
        QueryWrapper<TsAss> wrapper=new QueryWrapper<>();
        //时间升序排序
        wrapper.lambda().orderByAsc(TsAss::getCreateTime);
        return tsAssMapper.selectPage(page,wrapper);
    }

    @Override
    public List<TsAss> getAssBytherapistId(Long therapistId){
        QueryWrapper<TsAss> wrapper=new QueryWrapper<>();
        wrapper.eq("therapist_id",therapistId);
        List<TsAss> list = tsAssMapper.selectList(wrapper);
        return list;
    }







}
