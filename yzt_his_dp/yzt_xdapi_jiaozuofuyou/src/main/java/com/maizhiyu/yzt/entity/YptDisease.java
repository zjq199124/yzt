package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
@TableName("disease")
@ApiModel(description="治疗映射")
public class YptDisease implements Serializable {

    @TableId(type= IdType.AUTO)
    @ApiModelProperty(value="ID")
    private Integer id;

    @ApiModelProperty(value="编码")
    private String code;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="拼音")
    private String pinyin;

    @ApiModelProperty(value="缩写")
    private String abbr;

    @ApiModelProperty(value="his内编码")
    private String hiscode;

    @ApiModelProperty(value="his内名称")
    private String hisname;
}
