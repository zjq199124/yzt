package com.maizhiyu.yzt.aro;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class BuPrescriptionRO {

    @Data
    @ApiModel
    @Validated
    public static class AddPrescriptionZhongyao {
        @NotBlank
        @ApiModelProperty(value="HIS中处方ID")
        private String id;

        @NotBlank
        @ApiModelProperty(value="HIS中医生ID")
        private String doctorId;

        @NotBlank
        @ApiModelProperty(value="HIS中患者ID")
        private String patientId;

        @NotBlank
        @ApiModelProperty(value="HIS中门诊ID")
        private String outpatientId;

        @ApiModelProperty(value="注意事项")
        private String attention;

//        @NotNull
        @ApiModelProperty(value="总共天数")
        private Integer dayCount;

//        @NotNull
        @ApiModelProperty(value="每天几剂")
        private Integer doseCount;

//        @NotNull
        @ApiModelProperty(value="每剂几次")
        private Integer doseTimes;

        @ApiModelProperty(value="用药时间")
        private String useTime;

        @NotEmpty
        @ApiModelProperty(value="子项列表")
        private List<BuPrescriptionItemZhongyao> itemList;
    }

    @Data
    @ApiModel
    @Validated
    public static class AddPrescriptionChengyao {
        @NotBlank
        @ApiModelProperty(value="HIS中处方ID")
        private String id;

        @NotBlank
        @ApiModelProperty(value="HIS中医生ID")
        private String doctorId;

        @NotBlank
        @ApiModelProperty(value="HIS中患者ID")
        private String patientId;

        @NotBlank
        @ApiModelProperty(value="HIS中门诊ID")
        private String outpatientId;

        @ApiModelProperty(value="注意事项")
        private String attention;

        @NotEmpty
        @ApiModelProperty(value="子项列表")
        private List<BuPrescriptionItemChengyao> itemList;
    }

    @Data
    @ApiModel
    @Validated
    public static class AddPrescriptionXieding {
        @NotBlank
        @ApiModelProperty(value="HIS中处方ID")
        private String id;

        @NotBlank
        @ApiModelProperty(value="HIS中医生ID")
        private String doctorId;

        @NotBlank
        @ApiModelProperty(value="HIS中患者ID")
        private String patientId;

        @NotBlank
        @ApiModelProperty(value="HIS中门诊ID")
        private String outpatientId;

        @NotNull
        @ApiModelProperty(value="次数")
        private Integer count;

        @ApiModelProperty(value="注意事项")
        private String attention;

        @NotEmpty
        @ApiModelProperty(value="子项列表")
        private List<BuPrescriptionItemXieding> itemList;
    }

    @Data
    @ApiModel
    @Validated
    public static class AddPrescriptionShiyi {
        @NotBlank
        @ApiModelProperty(value="HIS中处方ID")
        private String id;

        @NotBlank
        @ApiModelProperty(value="HIS中医生ID")
        private String doctorId;

        @NotBlank
        @ApiModelProperty(value="HIS中患者ID")
        private String patientId;

        @NotBlank
        @ApiModelProperty(value="HIS中门诊ID")
        private String outpatientId;

        @ApiModelProperty(value="注意事项")
        private String attention;

        @NotEmpty
        @ApiModelProperty(value="子项列表")
        private List<BuPrescriptionItemShiyi> itemList;
    }


    @Data
    @ApiModel
    @Validated
    public static class BuPrescriptionItemZhongyao {
        @NotBlank
        @ApiModelProperty(value="编码")
        private String code;

        @NotBlank
        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="单位")
        private String unit;

        @ApiModelProperty(value="剂量")
        private BigDecimal dosage;

        @TableField("`usage`")
        @ApiModelProperty(value="用法")
        private String usage;

        @ApiModelProperty(value="备注")
        private String note;
    }

    @Data
    @ApiModel
    @Validated
    public static class BuPrescriptionItemChengyao {
        @NotBlank
        @ApiModelProperty(value="编码")
        private String code;

        @NotBlank
        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="剂量")
        private BigDecimal dosage;

        @ApiModelProperty(value="频度")
        private Integer frequency;

        @ApiModelProperty(value="天数")
        private Integer days;

        @ApiModelProperty(value="总量")
        private BigDecimal quantity;

        @TableField("`usage`")
        @ApiModelProperty(value="用法")
        private String usage;

        @ApiModelProperty(value="备注")
        private String note;
    }

    @Data
    @ApiModel
    @Validated
    public static class BuPrescriptionItemXieding {
        @NotBlank
        @ApiModelProperty(value="编码")
        private String code;

        @NotBlank
        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="单位")
        private String unit;

        @ApiModelProperty(value="剂量")
        private BigDecimal dosage;

        @TableField("`usage`")
        @ApiModelProperty(value="用法")
        private String usage;

        @ApiModelProperty(value="备注")
        private String note;
    }

    @Data
    @ApiModel
    @Validated
    public static class BuPrescriptionItemShiyi {
        @NotBlank
        @ApiModelProperty(value="编码")
        private String code;

        @NotBlank
        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="详情")
        private String detail;

        @ApiModelProperty(value="操作")
        private String operation;

        @ApiModelProperty(value="次数")
        private Integer quantity;

        @ApiModelProperty(value="备注")
        private String note;
    }
}
