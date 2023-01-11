package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.PsArticle;
import com.maizhiyu.yzt.entity.PsArticleComment;
import com.maizhiyu.yzt.entity.PsArticleStart;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.PsArticleCommentService;
import com.maizhiyu.yzt.serviceimpl.PsArticleService;
import com.maizhiyu.yzt.serviceimpl.PsArticleStartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "文章宣教接口")
@RestController
@RequestMapping("/article")
public class PsArticleController {

    @Resource
    private PsArticleService psArticleService;

    @Resource
    private PsArticleStartService psArticleStartService;

    @Resource
    private PsArticleCommentService psArticleCommentService;


    @ApiOperation(value = "新增或修改文章" , notes = "新增或修改文章")
    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody PsArticle psArticle){
        Boolean res = psArticleService.save(psArticle);
        return Result.success(res);
    }

    @ApiOperation(value = "批量删除文章" , notes = "批量删除文章")
    @PostMapping("/deleteArticles")
    public Result deleteArticles(@RequestBody List<Long> Aids){
        Boolean res = psArticleService.removeByIds(Aids);
        return Result.success(res);
    }

    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isRecommend", value = "是否推荐", required = false),
            @ApiImplicitParam(name = "isCarousel", value = "是否轮播图", required = false),
            @ApiImplicitParam(name = "kind" ,value = "分类" ,required = false),
    })
    @GetMapping("/getArticleList")
    public Result getArticleList(Page page,Integer isRecommend ,Integer isCarousel,String kind) {
        IPage<PsArticle> list = psArticleService.getArticleList(page,isRecommend,isCarousel,kind);
        return Result.success(list);
    }

    @ApiOperation(value = "点赞接口" ,notes = "点赞接口")
    @PostMapping("/start")
    public Result start(@RequestBody PsArticleStart psArticleStart){
            Boolean res = psArticleStartService.saveOrUpdate(psArticleStart);
            return Result.success(res);
    }

    @ApiOperation(value = "文章评论" , notes = "文章评论")
    @PostMapping("/addComment")
    public Result addComment(@RequestBody PsArticleComment psArticleComment){
        Boolean res = psArticleCommentService.save(psArticleComment);
        return Result.success(res);
    }

    @ApiOperation(value = "删除评论" ,notes = "删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId" , value = "评论id",required = true)
    })
    @GetMapping("/deleteComment")
    public Result deleteComment(@RequestParam Long commentId){
        Boolean res = psArticleCommentService.removeById(commentId);
        return  Result.success(res);
    }

    @ApiOperation(value = "获取评论详情" ,notes = "获取评论详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId" , value = "文章id" ,required = true)
    })
    @GetMapping("/getComment")
    public Result getComment(@RequestParam Long articleId){
        //获取一级评论
        List<PsArticleComment> comments = psArticleCommentService.getComment(articleId);
        return Result.success(comments);

    }

    @ApiOperation(value = "获取文章详情" , notes = "虎丘文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId" , value = "文章id" ,required = true)
    })
    @GetMapping("/getArticle")
    public Result getArticle(@RequestParam Long articleId){
        //根据id获取相应的文章数据
        PsArticle article = psArticleService.getById(articleId);
        return  Result.success(article);
    }




}
