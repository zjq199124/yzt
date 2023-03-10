package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain=true)
@TableName("ps_family")
@ApiModel(description="家庭成员信息表")
public class PsFamily {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="用户昵称")
    private String nickname;

    @ApiModelProperty(value="用户手机")
    private String phone;

    @ApiModelProperty(value="身份证号码")
    private String idCard;

    @ApiModelProperty(value="用户性别(0:女，1:男)")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Date birthday;

    @ApiModelProperty(value = "用户ID")
    private Long psUserId;

    @ApiModelProperty(value = "之间关系(0:本人 1: 父母  2:父亲  3: 母亲  4:子女\n" +
            "                 5:儿子 6:女儿  7:爱人 8:妻子 9:丈夫 10:其他  )")
    private Integer relType;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="是否删除（0：否 1：是）")
    @TableLogic
    private Integer isDel;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty("关系描述")
    @TableField(exist = false)
    private String relDescription;
}
