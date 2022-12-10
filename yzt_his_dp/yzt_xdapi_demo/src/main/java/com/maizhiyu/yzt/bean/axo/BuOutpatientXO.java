package com.maizhiyu.yzt.bean.axo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

public class BuOutpatientXO {

    @Data
    @ApiModel
    @Validated
    public static class AddOutpatientXO {
        @ApiModelProperty(value="预约时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
        private Date time;

        @ApiModelProperty(value="科室ID")
        private String departmentId;

        @ApiModelProperty(value="医生ID")
        private String doctorId;

        @ApiModelProperty(value="排班ID")
        private String scheduleId;

        @ApiModelProperty(value="患者ID")
        private String patientId;

        @ApiModelProperty(value="HIS中ID")
        private String hisId;

        @ApiModelProperty(value="HIS数据(建议json格式)")
        private String extra;
    }
}
