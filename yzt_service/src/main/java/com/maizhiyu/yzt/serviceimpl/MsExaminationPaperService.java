package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsExaminationPaper;
import com.maizhiyu.yzt.mapper.MsExaminationPaperMapper;
import com.maizhiyu.yzt.service.IMsExaminationPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className:MsExaminationPaperService
 * Package:com.maizhiyu.yzt.serviceimpl
 * Description:
 *
 * @DATE:2021/12/10 4:15 下午
 * @Author:2101825180@qq.com
 */
@Service
public class MsExaminationPaperService extends ServiceImpl<MsExaminationPaperMapper, MsExaminationPaper> implements IMsExaminationPaperService {

    @Autowired
    private MsExaminationPaperMapper msExaminationPaperMapper;

    @Override
    public Integer addMsExaminationPaper(MsExaminationPaper item) {
        return msExaminationPaperMapper.insert(item);
    }

    @Override
    public Integer delMsExaminationPaper(Long id) {
        return msExaminationPaperMapper.deleteById(id);
    }

    @Override
    public Integer setMsExaminationPaper(MsExaminationPaper item) {
        return msExaminationPaperMapper.updateById(item);
    }

    @Override
    public MsExaminationPaper getMsExaminationPaper(Long id) {
        return msExaminationPaperMapper.selectById(id);
    }

    @Override
    public IPage<MsExaminationPaper> getMsExaminationPaperList(Long sytechId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<MsExaminationPaper> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(sytechId != null, MsExaminationPaper::getSytechId, sytechId);
        IPage<MsExaminationPaper> list = msExaminationPaperMapper.selectPage(new Page<MsExaminationPaper>(pageNum, pageSize), wrapper);
        return list;
    }

    @Override
    public List<MsExaminationPaper> getMsExaminationPaperListBySytechId(Long sytechId) {
        List<MsExaminationPaper> list = msExaminationPaperMapper.selectList(
                new LambdaQueryWrapper<MsExaminationPaper>().eq(sytechId != null, MsExaminationPaper::getSytechId, sytechId));
        return list;
    }
}
