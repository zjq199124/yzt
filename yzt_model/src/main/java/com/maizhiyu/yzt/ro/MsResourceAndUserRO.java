package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 随访列表RO
 */
@Data
@ApiModel("新增角色下人员和菜单资源入参")
public class MsResourceAndUserRO implements Serializable {

    @ApiModelProperty(value = "角色ID")
    @NotNull
    private Long roleId;

    @ApiModelProperty(value = "人员参数")
    private List<Long> userRos;

    @ApiModelProperty(value = "菜单参数")
    private List<Long> resourceRos;

}
