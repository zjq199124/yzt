package com.maizhiyu.yzt.vo;

import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.entity.PsArticleComment;
import com.maizhiyu.yzt.entity.PsUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PsArticleCommentVO extends PsArticleComment {

    @ApiModelProperty("用户信息")
    private PsUser psUser;

    @ApiModelProperty("多级的评论")
    private List<PsArticleCommentVO> psArticleCommentVOS;
}
