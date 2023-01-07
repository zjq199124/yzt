package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 治疗签到表(BuSignature)表实体类
 * 
 * @author liuyzh
 * @since 2022-12-19 19:03:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "治疗签到")
@SuppressWarnings("serial")
public class BuSignature extends Model<BuSignature> implements Serializable {
    private static final long serialVersionUID = 425329063084389882L;
             
    @ApiModelProperty("主键")
    private Long id;
             
    @ApiModelProperty("患者id")
    private Long patientId;
             
    @ApiModelProperty("门诊id")
    private Long outpatientId;
             
    @ApiModelProperty("处方项id")
    private Long prescriptionItemId;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("科室id")
    private Long departmentId;
             
    @ApiModelProperty("登记人id(pad端用户id)")
    private Long registrantId;

    @ApiModelProperty("适宜技术id")
    private Long entityId;

    @ApiModelProperty("适宜技术名称")
    private String tsName;

    @ApiModelProperty("治疗状态：0：未开始；1：治疗中；2：治疗已结束")
    private Integer treatmentStatus;
             
    @ApiModelProperty("$column.comment")
    private Integer isDel;
             
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
             
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty("患者名称")
    @TableField(exist = false)
    private String name;

    @ApiModelProperty("患者性别")
    @TableField(exist = false)
    private String gender;

    @ApiModelProperty("患者年龄")
    @TableField(exist = false)
    private Integer age;

    @ApiModelProperty("科室名称")
    @TableField(exist = false)
    private String dname;

    @ApiModelProperty("疾病名称")
    @TableField(exist = false)
    private String disease;

    @ApiModelProperty("开单次数")
    @TableField(exist = false)
    private Integer quantity;

    @ApiModelProperty("已治疗次数")
    @TableField(exist = false)
    private Integer treatmentQuantity;

    @ApiModelProperty("就诊时间")
    @TableField(exist = false)
    private Date outpatientTime;

    @ApiModelProperty("技术详情")
    @TableField(exist = false)
    private String tsDescription;
}






















