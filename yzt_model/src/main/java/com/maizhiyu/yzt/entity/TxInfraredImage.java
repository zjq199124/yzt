package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (TxInfraredImage)实体类
 *
 * @author makejava
 * @since 2022-11-21 19:36:03
 */
@Data
@TableName("tx_infrared_image")
@Accessors(chain = true)
@ApiModel(description = "红外检测图片数据表")
public class TxInfraredImage implements Serializable {
    private static final long serialVersionUID = 885299640162904951L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 对应tx_infrared_date表的ID
     */
    private Long infraredDataId;
    /**
     * 类别
     */
    private String leibie;
    /**
     * 对应sys_mulitimedia表的ID
     */
    private Long multimediaId;

}

