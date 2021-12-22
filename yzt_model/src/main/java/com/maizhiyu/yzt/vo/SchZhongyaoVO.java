package com.maizhiyu.yzt.vo;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * className:SchZhongyaoVO
 * Package:com.maizhiyu.yzt.vo
 * Description:
 *
 * @DATE:2021/12/17 2:51 下午
 * @Author:2101825180@qq.com
 */

@Data
@Accessors(chain = true)
@ApiModel(description = "中药方案")
public class SchZhongyaoVO {

    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="所属疾病")
    private Long diseaseId;

    @ApiModelProperty(value="所属辨证")
    private Long syndromeId;

    @ApiModelProperty(value="中药名称")
    private String name;

    @ApiModelProperty(value="组成成分")
    private String component;

    @TableLogic
    private Integer flag;

    @ApiModelProperty(value="绑定的中药")
    private List<SchZhongyaoHerbsVO> list;

}
