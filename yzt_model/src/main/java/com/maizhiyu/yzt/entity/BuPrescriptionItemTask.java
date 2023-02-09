package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * (BuPrescriptionItemTask)表实体类
 *
 * @author makejava
 * @since 2023-02-01 17:31:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuPrescriptionItemTask extends Model<BuPrescriptionItemTask> {
    //处治小项目任务主键id
    @ApiModelProperty(value="处治小项目任务主键id")
    @TableId(type = IdType.AUTO)
    private Long id;
    //患者门诊处治预约信息主键id
    private Long outpatientAppointmentId;
    //处置小项目预约信息主键id
    private Long prescriptionItemAppointmentId;
    //客户id
    private Long customerId;
    //客户id
    private Long departmentId;
    //患者id
    private Long patientId;
    //门诊id
    private Long outpatientId;
    //诊断id
    private Long diagnoseId;
    //处置id
    private Long prescriptionId;
    //处置小项目id
    private Long prescriptionItemId;
    //适宜技术id
    private Long entityId;
    //适宜技术名称
    private String tsName;
    //预约转态0未预约；1已预约；2已逾期
    private Integer appointmentStatus;
    //预约日期
    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;
    //预约时段
    private String timeSlot;
    //预约星期数
    private Integer weekDay;
    //签到状态；0未签到；1已签到
    private Integer signatureStatus;
    //签到时间
    @ApiModelProperty(value="签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date signatureTime;
    //签到登记人id
    private Long registrantId;
    //治疗状态；0未治疗；1治疗中；2治疗已结束
    private Integer cureStatus;
    //治疗负责人id
    private Long cureUserId;
    //治疗开始时间
    @ApiModelProperty(value="治疗开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date cureStartTime;
    //治疗结束时间
    @ApiModelProperty(value="治疗结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date cureEndTime;
    //是否删除；1是；0否
    private Integer isDel;
    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}

