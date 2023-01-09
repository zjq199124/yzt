package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsArticle;

import java.util.List;

public interface IPsArticleService extends IService<PsArticle> {

    IPage<PsArticle> getArticleList(Page page,Long articleId,Integer isRcommend , Integer kind);
}
