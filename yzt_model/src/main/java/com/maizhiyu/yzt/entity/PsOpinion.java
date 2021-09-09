package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("ps_opinion")
@ApiModel(description="意见表")
public class PsOpinion implements Serializable {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="用户ID")
    private Long uid;

    @ApiModelProperty(value="用户手机")
    private String phone;

    @ApiModelProperty(value="意见内容")
    private String content;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

}
