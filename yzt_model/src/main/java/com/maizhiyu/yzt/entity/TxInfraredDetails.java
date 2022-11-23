package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (TxInfraredDetails)实体类
 *
 * @author makejava
 * @since 2022-11-21 19:32:26
 */
@Data
@TableName("tx_infrared_details")
@Accessors(chain = true)
@ApiModel(description = "红外检测详情数据表")
public class TxInfraredDetails implements Serializable {
    private static final long serialVersionUID = 770739943142843165L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 对应的红外详细数据ID
     */
    private Long infraredDataId;
    /**
     * 类别
     */
    private String leibie;
    /**
     * 方位
     */
    private String face;
    /**
     * 最高
     */
    private String zuigao;
    /**
     * 最低
     */
    private String zuidi;
    /**
     * 极差
     */
    private String jicha;
    /**
     * 标准差
     */
    private String biaozhuncha;
    /**
     * 标准差2
     */
    private String biaochuncha2;
    /**
     * 正态差
     */
    private String zhengtaicha;
    /**
     * 正态
     */
    private String zhengtai;


}

