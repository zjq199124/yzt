package com.maizhiyu.yzt.avo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

public class BuOutpatientVO {

    @Data
    @ApiModel
    public static class AddOutpatientVO {

        @ApiModelProperty(value="预约ID")
        private Long id;

        @ApiModelProperty(value="预约时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
        private Date time;

        @ApiModelProperty(value="HIS中ID")
        private String hisId;

        @ApiModelProperty(value="HIS数据(建议json格式)")
        private String extra;
    }

}
