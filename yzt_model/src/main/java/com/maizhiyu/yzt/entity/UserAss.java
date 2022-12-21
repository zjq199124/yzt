package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("user_ass")
@ApiModel(description="用户考核记录表")
public class UserAss {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "考核记录ID")
    private Long assId;

    @ApiModelProperty(value="具体考核操作步骤ID")
    private Long detailId;

    @ApiModelProperty(value="实得分")
    private Integer score;

    @ApiModelProperty(value="扣分点")
    private String  deduct;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="是否删除 0:未删除 1:已删除")
    private Integer isDel;
}
