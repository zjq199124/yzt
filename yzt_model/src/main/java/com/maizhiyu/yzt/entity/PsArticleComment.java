package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("ps_article_comment")
@ApiModel(description="文章宣教评价表")
public class PsArticleComment {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论者ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "被回复的评论ID，一级评论id为0")
    @TableField("reply_id")
    private Integer  replyId;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="是否删除（0：否 1：是）")
    @TableLogic
    private Integer isDel;
}
