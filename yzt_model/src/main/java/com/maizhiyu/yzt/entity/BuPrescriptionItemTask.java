package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    //疾病id
    private Long diseaseId;
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
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date appointmentDate;
    //预约时段
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String timeSlot;
    //预约星期数
    @TableField(updateStrategy = FieldStrategy.IGNORED)
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

    //是否作废；1是；0否
    private Integer isCancel;

    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    //预约操作的时间
    private Date appointmentCreateTime;

    //患者手机号
    @TableField(exist = false)
    private String phone;

    //患者姓名
    @TableField(exist = false)
    private String name;

    //疾病名称
    @TableField(exist = false)
    private String disease;

    //疾病分型
    @TableField(exist = false)
    private String syndrome;

    //门诊时间
    @TableField(exist = false)
    @ApiModelProperty(value="门诊时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date outPatientTime;

    //用户（医院）名称
    @TableField(exist = false)
    private String customerName;
}

