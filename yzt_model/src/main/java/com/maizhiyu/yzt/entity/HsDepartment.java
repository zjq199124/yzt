package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("hs_department")
@ApiModel(description="科室表")
public class HsDepartment {

    @ApiModelProperty(value="科室ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属客户")
    private Long customerId;

    @ApiModelProperty(value="科室状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="科室名称")
    private String dname;

    @ApiModelProperty(value="科室描述")
    private String descrip;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;
}
