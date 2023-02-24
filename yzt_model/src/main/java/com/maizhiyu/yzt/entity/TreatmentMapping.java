package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("treatment_mapping")
@ApiModel(description="适宜技术映射表")
public class TreatmentMapping implements Serializable {

    @ApiModelProperty(value="患者ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="患者编码")
    private String code;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="姓名")
    private String name;

    @ApiModelProperty(value="拼音")
    private String pinyin;

    @ApiModelProperty(value="缩写")
    private String abbr;

    @ApiModelProperty(value="his内部编码")
    private String hiscode;

    @ApiModelProperty(value="his内名称")
    private String hisname;

    @ApiModelProperty(value="状态：0：未删除；1：已删除")
    private Integer isDel;


    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="适宜技术价格")
    private BigDecimal price;
}
