package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsArticle;
import com.maizhiyu.yzt.entity.TsSytech;
import com.maizhiyu.yzt.mapper.PsArticleMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IPsArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;


@Service
public class PsArticleService extends ServiceImpl<PsArticleMapper, PsArticle> implements IPsArticleService {

    @Resource
    private PsArticleService psArticleService;

    @Override
    public IPage<PsArticle> getArticleList(Page page,Integer isRcommend ,Integer isCarousel, String kind){
        LambdaQueryWrapper<PsArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(isRcommend!=null,PsArticle::getIsRecommend, isRcommend)
                .eq(kind!=null,PsArticle::getKind, kind)
                .eq(isCarousel!=null,PsArticle::getIsCarousel,isCarousel)
                .select(PsArticle.class, e->!e.getColumn().equals("text") );
        IPage<PsArticle> list = psArticleService.page(page,wrapper);
        return list;
    }


}