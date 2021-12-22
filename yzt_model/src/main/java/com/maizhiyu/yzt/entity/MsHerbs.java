package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * className:MsHerbs
 * Package:com.maizhiyu.yzt.entity
 * Description:
 *
 * @DATE:2021/12/16 9:04 上午
 * @Author:2101825180@qq.com
 */

@Data
@TableName("ms_herbs")
@ApiModel(description="药材表")
public class MsHerbs {

    @ApiModelProperty(value="药材ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="药材名称")
    private String herbsName;

    @ApiModelProperty(value="单位")
    private String unit;

    @TableLogic
    private Integer flag;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
