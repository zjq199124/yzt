package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.util.List;

@Data
@Accessors(chain=true)
@TableName("bu_prescription")
@ApiModel(description="处方表")
public class BuPrescription implements Serializable {

    @ApiModelProperty(value="处方ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="处方单编码")
    private String code;

    @ApiModelProperty(value="处方类型(1:成药 2:中药饮片 3:中药颗粒剂 4:协定方 5:治疗方)")
    private Integer type;

    @ApiModelProperty(value="处方状态(1: 2:已打印)")
    private Integer status;

    @ApiModelProperty(value="结算状态")
    private Integer paymentStatus;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID")
    private Long doctorId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="预约ID")
    private Long outpatientId;

    @ApiModelProperty(value="诊断ID")
    private Long diagnoseId;

    @ApiModelProperty(value="注意事项")
    private String attention;

    @ApiModelProperty(value="总共天数")
    private Integer dayCount;

    @ApiModelProperty(value="每天几剂")
    private Integer doseCount;

    @ApiModelProperty(value="每剂几次")
    private Integer doseTimes;

    @ApiModelProperty(value="用药时间")
    private String useTime;

//    @ApiModelProperty(value="汇总价格")
//    private BigDecimal price;

    @ApiModelProperty("是否删除；1是；0否")
    private Integer isDel;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="医生姓名")
    @TableField(exist = false)
    private String doctorName;

    @ApiModelProperty(value="子项列表")
    @TableField(exist = false)
    private List<BuPrescriptionItem> itemList;

    @ApiModelProperty(value="HisId")
    private String hisId;
}
