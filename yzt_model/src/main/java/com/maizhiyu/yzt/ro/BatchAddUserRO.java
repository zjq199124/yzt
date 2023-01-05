package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@ApiModel
@Validated
public class BatchAddUserRO implements Serializable {

    private static final long serialVersionUID = -1;

    @ApiModelProperty("批量新增治疗师的id")
    public String  therapistIdS;

    @ApiModelProperty("考核人id")
    public Long examinerId;

    @ApiModelProperty("适宜技术id")
    public Long sytechId;
}
