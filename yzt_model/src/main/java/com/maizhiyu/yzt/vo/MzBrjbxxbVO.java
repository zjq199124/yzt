package com.maizhiyu.yzt.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "ohb患者数据")
public class MzBrjbxxbVO {

    @ApiModelProperty(value="ID")
    private Long brId;

    @ApiModelProperty(value="姓名")
    private String xm;

    @ApiModelProperty(value="性别")
    private String xb;

//    @ApiModelProperty(value="出生日期")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date csrq;

    @ApiModelProperty(value="年龄")
    private Integer nl;

    @ApiModelProperty(value="身份证")
    private String sfzh;

    public String getXb() {
        return xb != null ? xb.equals("1") ?  "1" : "0"  : null;
    }
}
