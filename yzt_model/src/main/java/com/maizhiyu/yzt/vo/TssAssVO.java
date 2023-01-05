package com.maizhiyu.yzt.vo;

import com.maizhiyu.yzt.entity.TsAssOperationDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(description = "考核成绩")
public class TssAssVO {

    @Data
    @ApiModel
    public static class detailGradeVO extends TsAssOperationDetail{

        @ApiModelProperty(value = "细节得分")
        private Integer getDetailScore;

        @ApiModelProperty(value="扣分点")
        private String deduct;

    }

    @Data
    @ApiModel
    public static class operationGradeVO{
        @ApiModelProperty(value="考核id")
        private Long assId;

        @ApiModelProperty(value="操作名称")
        private String operationName;

        @ApiModelProperty(value="操作ID")
        private Integer operationId;

//        @ApiModelProperty(value="操作细节")
//        private String operationDetails;

        @ApiModelProperty(value="操作得分")
        private String getOperationScore;

        @ApiModelProperty(value = "操作总分")
        private Integer OperationScore;

        @ApiModelProperty(value="操作细节")
        private List<Map<String,Object>> operationDetailsList;


    }






}
