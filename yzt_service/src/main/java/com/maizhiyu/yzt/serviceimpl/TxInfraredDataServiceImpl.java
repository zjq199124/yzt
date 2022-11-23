package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.entity.TxInfraredData;
import com.maizhiyu.yzt.entity.TxInfraredImage;
import com.maizhiyu.yzt.enums.InfraredImageEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.mapper.TxInfraredDataMapper;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.service.TxInfraredDataService;
import com.maizhiyu.yzt.service.TxInfraredImageService;
import com.maizhiyu.yzt.vo.InfraredCheckVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (TxInfraredData)表服务实现类
 *
 * @author makejava
 * @since 2022-11-21 19:24:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TxInfraredDataServiceImpl extends ServiceImpl<TxInfraredDataMapper, TxInfraredData> implements TxInfraredDataService {

    @Resource
    SysMultimediaService sysMultimediaService;

    @Resource
    TxInfraredImageService txInfraredImageService;

    @Override
    public TxInfraredData saveTxInfrareData(TxInfraredData txInfraredData, InputStream inputStream, String fileName) {
        SysMultimedia sysMultimedia = sysMultimediaService.saveMultimedia(inputStream, fileName, OSSCatalogEnum.INFRARED.getPath(), true, OSSCatalogEnum.INFRARED.getRemark());
        txInfraredData.setMultimediaId(sysMultimedia.getId());
        save(txInfraredData);
        return txInfraredData;
    }

    @Override
    public Map<String, Object> getCheckAndDate(String mobile, String idCard) {
        LambdaQueryWrapper<TxInfraredData> wrapper = Wrappers.lambdaQuery();
        if (!idCard.isEmpty()) {
            wrapper.eq(TxInfraredData::getIdCard, idCard);
        }
        if (!StrUtil.isEmptyIfStr(mobile)) {
            wrapper.eq(TxInfraredData::getIdCard, idCard);
        }
        List<TxInfraredData> txInfraredDataList = list(wrapper);
        HashMap<String, Object> result = new HashMap<>();
        List<String> dateList = txInfraredDataList.stream().map(item -> (DateUtil.format(item.getTestDate(), "yyyy-MM-dd"))).collect(Collectors.toList());
        List<String> typeList = Arrays.stream(InfraredImageEnum.values()).map(item -> (item.getName())).collect(Collectors.toList());
        result.put("dateList", dateList);
        result.put("typeList", typeList);
        return result;
    }

    @Override
    public InfraredCheckVO getInfrareDateCheck(String part, String date) {
        //查询红外检测数据
        LambdaQueryWrapper<TxInfraredData> wrapper = Wrappers.lambdaQuery();
        wrapper.like(TxInfraredData::getTestDate, date);
        TxInfraredData txInfraredData = getOne(wrapper);
        //查询部位图片
        LambdaQueryWrapper<TxInfraredImage> wrapper1 = Wrappers.lambdaQuery();
        wrapper1.like(TxInfraredImage::getLeibie, part);
        wrapper1.eq(TxInfraredImage::getInfraredDataId, txInfraredData.getId());
        TxInfraredImage txInfraredImage = txInfraredImageService.getOne(wrapper1);
        String imageUlr = sysMultimediaService.getFileUrlByid(txInfraredImage.getMultimediaId());
        InfraredCheckVO infraredCheckVO = new InfraredCheckVO();
        infraredCheckVO.setBianhanre(txInfraredData.getBianhanre());
        infraredCheckVO.setDuichenxing(txInfraredData.getDuichenxing());
        infraredCheckVO.setGuilvxing(txInfraredData.getGuilvxing());
        infraredCheckVO.setImageUrl(imageUlr);
        return infraredCheckVO;
    }
}

