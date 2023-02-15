package com.maizhiyu.yzt.vo;


import com.maizhiyu.yzt.entity.PsFamily;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "家人信息")
public class FamilyVo extends PsFamily {

    @ApiModelProperty(value = "当前用户id")
    private Long  UserId;

    @ApiModelProperty(value = "修改用户id")
    private Long familyId;

    @ApiModelProperty(value = "关联的主键id")
    private Long relationUserId;

    @ApiModelProperty("关系的对应id")
    private Integer relId;
}
