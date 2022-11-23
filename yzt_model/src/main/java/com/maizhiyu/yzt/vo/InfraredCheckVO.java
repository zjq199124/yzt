package com.maizhiyu.yzt.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "红外检测报告查询信息")
public class InfraredCheckVO {
    /**
     * 对称性
     */
    private String duichenxing;
    /**
     * 规律性
     */
    private String guilvxing;
    /**
     * 辨寒热
     */
    private String bianhanre;
    /**
     * 图片路径
     */
    private String imageUrl;
}
