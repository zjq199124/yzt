package com.maizhiyu.yzt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TxInfraredData;
import com.maizhiyu.yzt.vo.InfraredCheckVO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * (TxInfraredData)表服务接口
 *
 * @author makejava
 * @since 2022-11-21 19:24:57
 */
public interface TxInfraredDataService extends IService<TxInfraredData> {
    /**
     * 保存红外检测数据
     *
     * @param txInfraredData 检测数据
     * @param inputStream    检测文件流
     * @param file           文件名
     * @return
     */
    TxInfraredData saveTxInfrareData(TxInfraredData txInfraredData, InputStream inputStream, String file);

    /**
     * 以手机号或身份证号获取用户检测部位和检测日期
     *
     * @param mobile 手机号
     * @param idCard 身份证号
     * @return
     */
    Map<String, Object> getCheckAndDate(String mobile, String idCard);

    /**
     * @param part       部位
     * @param firstDate  第一个数据日期
     * @param secondDate 第二个数据日期
     * @return
     */
    List<InfraredCheckVO> getInfrareDateCheck(String part, String firstDate, String secondDate);


}

