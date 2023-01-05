package com.maizhiyu.yzt.ro;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * 随访列表RO
 */
@Data
@ApiModel("随访列表查询入参")
@Validated
public class VisitRO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String date;

    /**
     * 患者姓名
     */
    @ApiModelProperty(value = "搜索字段")
    private String term;


    /**
     * 随访状态(1-正常，2-失访，3-死亡）
     */
    @ApiModelProperty(value = "随访状态(1-正常，2-失访，3-死亡）")
    private String status;

}
