package com.example.yzt_ohb.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * ???????
 * </p>
 *
 * @author zyx
 * @since 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MZ_BRJBXXB")
public class MzBrjbxxbDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ?????
     */
    @TableId(value = "BR_ID", type = IdType.AUTO)
    private Long brId;

    /**
     * ???????
     */
    private Integer brjsfsId;

    /**
     * ??
     */
    private String xm;

    /**
     * ????
     */
    private Date csrq;

    /**
     * ??
     */
    private Integer nl;

    /**
     * ??
     */
    private String hy;

    /**
     * ??
     */
    private String xb;

    /**
     * ????
     */
    private String sfzh;

    /**
     * ????
     */
    private String ylbxzh;

    /**
     * ??
     */
    private Integer mz;

    /**
     * ??
     */
    private Integer zy;

    /**
     * ??
     */
    private String jg;

    /**
     * ??
     */
    private Integer gj;

    /**
     * ????
     */
    private String jtdz;

    /**
     * ????
     */
    private Integer gzdw;

    /**
     * ???
     */
    private String lxr;

    /**
     * ???????
     */
    private String lxrxxxx;

    /**
     * ????
     */
    private String yzbm;

    /**
     * ????
     */
    private Date gxrq;

    /**
     * ????
     */
    private String lxdh;

    /**
     * ??????
     */
    private String ylxrgx;

    /**
     * ????
     */
    private String jtdh;

    /**
     * ????
     */
    private String jtym;

    /**
     * ??
     */
    private String xx;

    /**
     * ??????
     */
    private String gzdwmc;

    /**
     * ????
     */
    private String gmyw;

    /**
     * ???
     */
    private String shengBm;

    /**
     * ???
     */
    private String shengMc;

    /**
     * ???
     */
    private String shiBm;

    /**
     * ???
     */
    private String shiMc;

    /**
     * ???
     */
    private String quBm;

    /**
     * ???
     */
    private String quMc;

    /**
     * ?????
     */
    private String jwhBm;

    /**
     * ?????
     */
    private String jwhMc;

    /**
     * ???
     */
    private String zhenBm;

    /**
     * ???
     */
    private String zhenMc;

    private Integer whcd;

    private String cdbh;

    /**
     * ????
     */
    private String dwdh;

    private String rh;

    private String hkdz;


}
