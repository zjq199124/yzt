package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRo implements Serializable {

    private static final long serialVersionUID = -1;

    @ApiModelProperty("当前页")
    public Integer currentPage = 1;

    @ApiModelProperty("页面容量")
    public Integer pageSize = 10;
}
