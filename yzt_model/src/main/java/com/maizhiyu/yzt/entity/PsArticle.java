package com.maizhiyu.yzt.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("ps_article")
@ApiModel(description="文章宣教表")
public class PsArticle {
    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "作者信息")
    @TableField("authinfo")
    private String authinfo;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("text")
    private String text;

    @ApiModelProperty(value = "文章分类")
    @TableField("kind")
    private String  kind;

    @ApiModelProperty(value = "封面地址")
    @TableField("cover")
    private String cover;

    @ApiModelProperty(value = "是否轮播展示(0:不展示 1:展示)")
    @TableField("isCarousel")
    private Integer  isCarousel;

    @ApiModelProperty(value = "是否推荐展示(0:不展示 1:展示)")
    @TableField("isRecommend")
    private Integer  isRecommend;

    @ApiModelProperty(value = "阅读量")
    @TableField("read_count")
    private Long  readCount;


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
