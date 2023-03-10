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
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain=true)
@TableName("bu_prescription_item_appointment")
@ApiModel(description="处方明细预约表")
public class BuPrescriptionItemAppointment extends Model<BuPrescriptionItemAppointment> {

    @ApiModelProperty(value="预约表主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="门诊下的一条处治数据对应的预约数据id")
    private Long outpatientAppointmentId;

    @ApiModelProperty(value="处方条目id")
    private Long prescriptionItemId;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="门诊预约id")
    private Long outpatientId;

    @ApiModelProperty(value="适宜技术ID")
    private Long entityId;

    @ApiModelProperty(value="适宜技术名称")
    private String name;

    @ApiModelProperty(value="处方id")
    private Long prescriptionId;

    @ApiModelProperty(value="诊断id")
    private Long diagnoseId;

    @ApiModelProperty("预约状态 1：未预约；2：预约中;3:预约完成")
    private Integer state;

    @ApiModelProperty("开方次数")
    private Integer quantity;

    @ApiModelProperty("已治疗次数")
    private Integer treatmentQuantity;

    @ApiModelProperty("已预约次数")
    private Integer appointmentQuantity;

    @ApiModelProperty("剩余预约次数")
    private Integer surplusQuantity;

    @ApiModelProperty("是否删除；1：是；0：否")
    private Integer isDel;

    @ApiModelProperty("是否作废；1：是；0：否")
    private Integer isCancel;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    @ApiModelProperty(value = "适宜技术的名称")
    private String tsName;

    @TableField(exist = false)
    @ApiModelProperty("已预约的处治任务List，（未签到，未治疗）")
    List<BuPrescriptionItemTask> appointmentTaskList;

    @TableField(exist = false)
    @ApiModelProperty("已治疗的处治任务")
    private List<BuPrescriptionItemTask> cureTaskList;
}
