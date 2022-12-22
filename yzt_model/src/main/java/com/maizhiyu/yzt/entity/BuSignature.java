package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
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

    @ApiModelProperty("治疗状态：0：未开始；1：治疗中；2：治疗已结束")
    private Integer treatmentStatus;
             
    @ApiModelProperty("$column.comment")
    private Integer isDel;
             
    @ApiModelProperty("创建时间")
    private Date createTime;
             
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
