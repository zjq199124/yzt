package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;
    //患者门诊处治预约信息主键id
    private Long outpatientAppointmentId;
    //处置小项目预约信息主键id
    private Long prescriptionItemAppointmentId;
    //客户id
    private Long customerId;
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
    private Date appointmentDate;
    //预约时段
    private String timeSlot;
    //预约星期数
    private Integer weekDay;
    //签到状态；0未签到；1已签到
    private Integer signatureStatus;
    //签到时间
    private Date signatureTime;
    //治疗状态；0未治疗；1治疗中；2治疗已结束
    private Integer cureStatus;
    //治疗开始时间
    private Date cureStartTime;
    //治疗结束时间
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

