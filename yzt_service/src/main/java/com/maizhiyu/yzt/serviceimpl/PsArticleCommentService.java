package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsArticleComment;
import com.maizhiyu.yzt.mapper.PsArticleCommentMapper;
import com.maizhiyu.yzt.service.IPsArticleCommentService;
import com.maizhiyu.yzt.vo.PsArticleCommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PsArticleCommentService extends ServiceImpl<PsArticleCommentMapper, PsArticleComment> implements IPsArticleCommentService {

    @Resource
    private PsArticleCommentService psArticleCommentService;



//    @Override
//    public List<PsArticleComment> getComment(Long articleId) {
//        //获取所有的评论
//        LambdaQueryWrapper<PsArticleComment> wrapperlist = new LambdaQueryWrapper<>();
//        wrapperlist.eq(PsArticleComment::getArticleId, articleId);
//        List<PsArticleComment> list =  psArticleCommentService.list(wrapperlist);
//        //数据转换个格式
//        List<PsArticleComment> resultLists = CommentHelper.buildTree(list);
//
//        return  null;
//
//    }

};

