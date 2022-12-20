package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
import java.io.Serializable;
 
/**
 * 患者随访表(BuVisit)表实体类
 * 
 * @author liuyzh
 * @since 2022-12-19 18:57:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "")
@SuppressWarnings("serial")
public class BuVisit extends Model<BuVisit> implements Serializable {
    private static final long serialVersionUID = -75865880254136098L;
             
    @ApiModelProperty("id")
    private Long id;
             
    @ApiModelProperty("治疗id")
    private Long cureId;
             
    @ApiModelProperty("随访状态(1-正常，2-失访，3-死亡）")
    private Integer status;
             
    @ApiModelProperty("疗效评定(1-治愈，2-好转，3-未愈)")
    private Integer result;
             
    @ApiModelProperty("随访类型")
    private Integer type;
             
    @ApiModelProperty("随访详情")
    private String content;
}
