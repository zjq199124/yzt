package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.entity.TxInfraredData;
import com.maizhiyu.yzt.entity.TxInfraredImage;
import com.maizhiyu.yzt.enums.FileTypeEnum;
import com.maizhiyu.yzt.enums.InfraredImageEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.TxInfraredDataMapper;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.service.TxInfraredDataService;
import com.maizhiyu.yzt.service.TxInfraredImageService;
import com.maizhiyu.yzt.vo.InfraredCheckVO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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



    @SneakyThrows
    @Override
    public TxInfraredData saveTxInfrareData(TxInfraredData txInfraredData, InputStream inputStream, String fileName) {
        try {
            SysMultimedia sysMultimedia = sysMultimediaService.saveMultimedia(inputStream, fileName, OSSCatalogEnum.INFRARED.getPath(), true, OSSCatalogEnum.INFRARED.getRemark(), FileTypeEnum.FILE.getCode());
            txInfraredData.setMultimediaId(sysMultimedia.getId());
            //save(txInfraredData);
            return txInfraredData;
        } catch (Exception e) {
            throw new BusinessException(e);
        }finally {
            inputStream.close();
        }

    }

    @Override
    public Map<String, Object> getCheckAndDate(String mobile, String idCard) {
        LambdaQueryWrapper<TxInfraredData> wrapper = Wrappers.lambdaQuery();
        if (!StrUtil.isEmptyIfStr(idCard)) {
            wrapper.eq(TxInfraredData::getIdCard, idCard);
        }
        if (!StrUtil.isEmptyIfStr(mobile)) {
            wrapper.eq(TxInfraredData::getPhone, mobile);
        }
        List<TxInfraredData> txInfraredDataList = list(wrapper);
        HashMap<String, Object> result = new HashMap<>();
        List<String> dateList = txInfraredDataList.stream().map(item -> (DateUtil.format(item.getTestDate(), "yyyy-MM-dd HH:mm:ss"))).collect(Collectors.toList());
        List<String> typeList = Arrays.stream(InfraredImageEnum.values()).map(item -> (item.getName())).collect(Collectors.toList());
        result.put("dateList", dateList);
        result.put("typeList", typeList);
        return result;
    }

    @Override
    public List<InfraredCheckVO> getInfrareDateCheck(String part, String firstDate, String secondDate) {
        //查询红外检测数据
        LambdaQueryWrapper<TxInfraredData> wrapper = Wrappers.lambdaQuery();
        wrapper.like(TxInfraredData::getTestDate, firstDate);
        wrapper.or(w->w.like(TxInfraredData::getTestDate, secondDate));
        List<TxInfraredData> txInfraredDataList = list(wrapper);
        //查询部位图片
        List<InfraredCheckVO> result = new ArrayList<>();
        for (TxInfraredData txInfraredData : txInfraredDataList) {
            LambdaQueryWrapper<TxInfraredImage> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(TxInfraredImage::getLeibie, part);
            wrapper1.eq(TxInfraredImage::getInfraredDataId, txInfraredData.getId());
            TxInfraredImage txInfraredImage = txInfraredImageService.getOne(wrapper1);
            String imageUlr = sysMultimediaService.getFileUrlByid(txInfraredImage.getMultimediaId());
            InfraredCheckVO infraredCheckVO = new InfraredCheckVO();
            infraredCheckVO.setBianhanre(txInfraredData.getBianhanre());
            infraredCheckVO.setDuichenxing(txInfraredData.getDuichenxing());
            infraredCheckVO.setGuilvxing(txInfraredData.getGuilvxing());
            infraredCheckVO.setImageUrl(imageUlr);
            infraredCheckVO.setCreateDate(DateUtil.format(txInfraredData.getTestDate(), "yyyy-MM-dd HH:mm:ss"));
            result.add(infraredCheckVO);
        }
        return result;
    }
}

