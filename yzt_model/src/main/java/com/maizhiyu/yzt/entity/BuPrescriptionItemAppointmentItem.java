package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("bu_prescription_item_appointment_item")
@ApiModel(description="处方明细预约表")
public class BuPrescriptionItemAppointmentItem extends Model<BuPrescriptionItemAppointmentItem> {

    @ApiModelProperty(value="预约表主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="处方技术明细预约表id")
    private Long prescriptionItemAppointmentId;

    @ApiModelProperty(value="此次门诊预约数据id")
    private Long outpatientAppointmentId;

    @ApiModelProperty("处方适宜技术明细id")
    private Long prescriptionItemId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="门诊预约id")
    private Long outpatientId;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date appointmentDate;

    @ApiModelProperty(value="预约时段")
    private String timeSlot;

    @ApiModelProperty("星期数")
    private Integer weekday;

    @ApiModelProperty("治疗状态 1：待治疗；2：已治疗")
    private Integer state;

    @ApiModelProperty("是否删除；1：是；0：否")
    private Integer IsDel;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "患者名称")
    @TableField(exist = false)
    private String name;

    @ApiModelProperty(value = "适宜技术名称")
    @TableField(exist = false)
    private String tsName;

    @ApiModelProperty(value = "手机号码")
    @TableField(exist = false)
    private String phone;
}
