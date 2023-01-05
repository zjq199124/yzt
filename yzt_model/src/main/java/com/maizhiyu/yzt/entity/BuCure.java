package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 治疗表(BuCure)表实体类
 * 
 * @author liuyzh
 * @since 2022-12-19 18:41:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "")
@TableName("bu_cure")
@SuppressWarnings("serial")
public class BuCure extends Model<BuCure> implements Serializable {
    private static final long serialVersionUID = -23941013044417057L;
             
    @ApiModelProperty("id")
    private Long id;
             
    @ApiModelProperty("门诊id")
    private Long outpatientId;

    @ApiModelProperty("患者id")
    private Long patientId;

    @ApiModelProperty("签到id")
    private Long signatureId;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("登记人id(hs_user id)")
    private Long registrantId;
             
    @ApiModelProperty("治疗负责人id（hs_user id )")
    private Long cureUserId;

    @ApiModelProperty("治适宜技术id")
    private Long entityId;

    @ApiModelProperty("治适宜技术名称")
    private String tsName;

    @ApiModelProperty("治适宜技术详情")
    private String tsDescription;

    @ApiModelProperty("创建时间")
    private Date createTime;
             
    @ApiModelProperty("更新时间")
    private Date updateTime;
             
    @ApiModelProperty("1：进行中；2：已结束")
    private Integer status;

    @ApiModelProperty("是否删除")
    private Integer isDel;

    @ApiModelProperty("处方项目id")
    private Long prescriptionItemId;


    @ApiModelProperty("患者姓名")
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

    @ApiModelProperty("星期")
    @TableField(exist = false)
    private Integer weekDay;
}



















