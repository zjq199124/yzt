package com.maizhiyu.yzt.aro;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
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
        private String hisId;

        @ApiModelProperty(value="云平台中处方ID")
        private Long id;

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

        @ApiModelProperty(value="HIS中处方ID")
        private String hisId;

        @ApiModelProperty(value="云平台处方ID")
        private Long id;

        @ApiModelProperty(value = "基础信息")
        private BaseInfo baseInfo;

        @ApiModelProperty(value = "诊断信息")
        private DiagnoseInfo diagnoseInfo;

        @ApiModelProperty(value="注意事项")
        private String attention;

        @ApiModelProperty(value="子项列表")
        private List<BuPrescriptionItemShiyi> itemList;

        @Data
        @ApiModel
        @Validated
        public static class DiagnoseInfo{

            @ApiModelProperty("诊断主键id")
            private Long id;

            @ApiModelProperty("客户名称")
            private String customerName;

            @ApiModelProperty("客户id")
            private Long customerId;

            @ApiModelProperty("科室id")
            private Long departmentId;

            @ApiModelProperty(value = "云平台疾病id")
            private Long diseaseId;

            @ApiModelProperty(value = "云平台疾病名称")
            private String disease;

            @ApiModelProperty("分型id")
            private Long syndromeId;

            @ApiModelProperty("分型名称")
            private String syndrome;

            @ApiModelProperty("症状id列表,多个使用','分割")
            private String symptomIds;

            @ApiModelProperty("症状名称列表,多个使用','分割")
            private String symptoms;
        }

        @Data
        @ApiModel
        @Validated
        public static class BaseInfo{
            @NotBlank
            @ApiModelProperty(value="HIS中医生ID")
            private Long doctorId;

            @NotBlank
            @ApiModelProperty(value="HIS中患者ID")
            private Long patientId;

            @NotBlank
            @ApiModelProperty(value="HIS中门诊ID")
            private Long outpatientId;
        }
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

        @ApiModelProperty("主键id")
        private Long id;

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
