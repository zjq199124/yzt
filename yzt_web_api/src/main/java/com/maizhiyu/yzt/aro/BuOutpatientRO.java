package com.maizhiyu.yzt.aro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class BuOutpatientRO {

    @Data
    @ApiModel
    @Validated
    public static class AddOutpatientRO {
        @NotNull
        @ApiModelProperty(value="预约时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
        private Date time;

        @ApiModelProperty(value="科室ID")
        private String departmentId;

        @NotBlank
        @ApiModelProperty(value="医生ID")
        private String doctorId;

        @ApiModelProperty(value="排班ID")
        private String scheduleId;

        @NotBlank
        @ApiModelProperty(value="患者ID")
        private String patientId;

        @NotBlank
        @ApiModelProperty(value="HIS中ID")
        private String hisId;

        @ApiModelProperty(value="HIS数据(建议json格式)")
        private String extra;
    }
}
