package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.entity.MsZhongyaoHerbs;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.MsHerbsMapper;
import com.maizhiyu.yzt.mapper.MsZhongyaoHerbsMapper;
import com.maizhiyu.yzt.service.IMsZhongyaoHerbsService;
import com.maizhiyu.yzt.vo.SchZhongyaoHerbsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * className:MsZhongyaoHerbsService
 * Package:com.maizhiyu.yzt.serviceimpl
 * Description:
 *
 * @DATE:2021/12/17 2:49 下午
 * @Author:2101825180@qq.com
 */

@Service
public class MsZhongyaoHerbsService extends ServiceImpl<MsZhongyaoHerbsMapper,MsZhongyaoHerbs> implements IMsZhongyaoHerbsService {

    @Autowired
    private MsZhongyaoHerbsMapper msZhongyaoHerbsMapper;

    @Autowired
    private MsHerbsMapper msHerbsMapper;

    @Override
    public List<SchZhongyaoHerbsVO> getMsZhongyaoHerbsListBySchZhongyaoId(Long id) {
        return msZhongyaoHerbsMapper.getMsZhongyaoHerbsListBySchZhongyaoId(id);
    }

    @Override
    public Integer removeSchZhongyaoHerbs(Long id) {
        return msZhongyaoHerbsMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adds(List<MsZhongyaoHerbs> list1) {

        List<Long> collect = list1.stream().map(MsZhongyaoHerbs::getHerbsId).collect(Collectors.toList());

        List<MsHerbs> msHerbsList = msHerbsMapper.selectList(Wrappers.<MsHerbs>lambdaQuery().in(MsHerbs::getId, collect).last("for update "));
        if(msHerbsList == null) {
            throw new BusinessException("删除失败");
        }

        int num = 0;
        for (int i = 0; i < list1.size(); i++) {
            MsZhongyaoHerbs msZhongyaoHerbs = list1.get(i);
            msZhongyaoHerbs.setCreateTime(new Date());
            int insert = msZhongyaoHerbsMapper.insert(msZhongyaoHerbs);
            if(insert > 0) {
                num ++;
            }
        }

        if(list1.size() != num) {
            throw new BusinessException("操作失败");
        }

    }
}
