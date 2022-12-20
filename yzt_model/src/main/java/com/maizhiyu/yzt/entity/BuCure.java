package com.maizhiyu.yzt.entity;

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
@SuppressWarnings("serial")
public class BuCure extends Model<BuCure> implements Serializable {
    private static final long serialVersionUID = -23941013044417057L;
             
    @ApiModelProperty("id")
    private Long id;
             
    @ApiModelProperty("门诊id")
    private Long outPatientId;
             
    @ApiModelProperty("签到id")
    private Long signatureId;
             
    @ApiModelProperty("登记人id(hs_user id)")
    private Long registrantId;
             
    @ApiModelProperty("治疗负责人id（hs_user id )")
    private Long cureUserId;
             
    @ApiModelProperty("创建时间")
    private Date createTime;
             
    @ApiModelProperty("更新时间")
    private Date updateTime;
             
    @ApiModelProperty("是否删除")
    private Integer isDel;
}
