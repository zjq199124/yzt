package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("rel_user")
@ApiModel(description = "家庭用户关系表")
public class RelUser {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value ="用户id")
    private Long userId;

    @ApiModelProperty(value = "另外相关用户id")
    private Long AnotherUserId;


    @ApiModelProperty(value = "关系id(1: 父母  2:爱人  3: 子女  4:其他)")
    private Integer relId;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="是否删除：1是；0否")
    private Integer isDel;


}
