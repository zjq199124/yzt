package com.maizhiyu.yzt.entity;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain=true)
@TableName("bu_template")
@ApiModel(description="处方表")
public class BuTemplate implements Serializable {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="处方状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID")
    private Long doctorId;

    @ApiModelProperty(value="模板标题")
    private String title;

    @ApiModelProperty(value="模板描述")
    private String descrip;

    @ApiModelProperty(value="模板内容")
    @JsonIgnore
    private String content;

    @ApiModelProperty(value="模板内容")
    @TableField(exist = false)
    private JSONArray contents;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    private Date createTime;


    public static void main(String[] args) {
        String jstr = "[{\"a\":1,\"b\":2},{}]";
        JSONObject jarr = JSONArray.parseObject(jstr);
        System.out.println(jarr);
    }
}
