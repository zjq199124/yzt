package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("bu_prescription_item")
@ApiModel(description="处方条目表")
public class BuPrescriptionItem implements Serializable {

    @ApiModelProperty(value="条目ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="处方类型(1:成药 2:中药饮片 3:中药颗粒剂 4:协定方 5:治疗)")
    private Integer type;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID")
    private Long doctorId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="门诊ID")
    private Long outpatientId;

    @ApiModelProperty(value="处方ID")
    private Long prescriptionId;

    @ApiModelProperty(value="实体ID")
    private Long entityId;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="详情")
    private String detail;

    @ApiModelProperty(value="操作")
    private String operation;

    @TableField("`usage`")
    @ApiModelProperty(value="用法")
    private String usage;

    @ApiModelProperty(value="单位")
    private String unit;

    @ApiModelProperty(value="剂量")
    private BigDecimal dosage;

    @ApiModelProperty(value="频度")
    private Integer frequency;

    @ApiModelProperty(value="天数")
    private Integer days;

    @ApiModelProperty(value="总量")
    private BigDecimal quantity;

    @ApiModelProperty(value="价格")
    private BigDecimal price;

    @ApiModelProperty(value="备注")
    private String note;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    private Long customerHerbsId;

    private Long herbsId;
}
