package com.maizhiyu.yzt.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.entity.TxInfraredData;
import com.maizhiyu.yzt.entity.TxInfraredDetails;
import com.maizhiyu.yzt.enums.CheckTypeEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.service.TxInfraredDataService;
import com.maizhiyu.yzt.service.TxInfraredDetailsService;
import com.maizhiyu.yzt.service.TxInfraredImageService;
import com.maizhiyu.yzt.serviceimpl.BuCheckService;
import com.maizhiyu.yzt.serviceimpl.BuOutpatientService;
import com.maizhiyu.yzt.utils.infrared.InfraredPdfAnalysis;
import com.maizhiyu.yzt.utils.infrared.InfraredResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "红外成像设备接口")
@RestController
@RequestMapping("/hwcx")
@Slf4j
public class TeHwcxController {

    @Resource
    SysMultimediaService sysMultimediaService;

    @Resource
    TxInfraredDataService txInfraredDataService;

    @Resource
    TxInfraredDetailsService txInfraredDetailsService;

    @Resource
    TxInfraredImageService txInfraredImageService;

    @Resource
    BuCheckService buCheckService;

    @Resource
    BuOutpatientService buOutpatientService;

    @ApiOperation(value = "检测文件接收上传", notes = "检测文件接收上传")
    @ApiImplicitParams({})
    @PostMapping(value = "/receiveFile")
    public Result<String> receiveFile(@RequestParam MultipartFile file) {
        try {
            return Result.success(sysMultimediaService.saveMultimedia(file, OSSCatalogEnum.INFRARED.getPath(), true, "红外检查报告"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO 文件分析数据保存
    @ApiOperation(value = "接收红外检测报告", notes = "接收红外检测报告")
    @ApiImplicitParams({})
    @PostMapping(value = "/receiveInfrared")
    @Transactional
    public Result<Boolean> receiveInfrared(InfraredResult infraredResult) {
        try {
            InputStream inputStream = new URL(infraredResult.getTcmUrl()).openStream();
            //获取文件
            PDDocument doc = PDDocument.load(inputStream);
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper textStripper = new PDFTextStripper();
            textStripper.setStartPage(3);
            textStripper.setEndPage(14);
            String content = textStripper.getText(doc);
            System.out.println("内容:" + content);
            System.out.println("全部页数" + doc.getNumberOfPages());
            String[] lines = content.split("\\r\\n");
            //先保存检测数据
            TxInfraredData txInfraredData = InfraredPdfAnalysis.getThwbase(lines);
            txInfraredData.setPhone(infraredResult.getMobile());
            txInfraredData.setIdCard(infraredResult.getIDCard());
            txInfraredData.setTestDate(DateUtil.parse(infraredResult.getCreateTime()));
            String[] paths = infraredResult.getTcmUrl().split("/");
            String fileName = paths[paths.length - 1];
            txInfraredDataService.saveTxInfrareData(txInfraredData, inputStream, fileName);
            //保存数据详情
            List<TxInfraredDetails> details1 = InfraredPdfAnalysis.getZangfu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details2 = InfraredPdfAnalysis.getSanjiao(lines, txInfraredData.getId());
            List<TxInfraredDetails> details3 = InfraredPdfAnalysis.getZJiepaofenqu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details4 = InfraredPdfAnalysis.getBJiepaofenqu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details5 = InfraredPdfAnalysis.getZYJiepaofenqu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details6 = InfraredPdfAnalysis.getZShaoyangpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details7 = InfraredPdfAnalysis.getYShaoyangpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details8 = InfraredPdfAnalysis.getZTaiyangpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details9 = InfraredPdfAnalysis.getZYangmingpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details10 = InfraredPdfAnalysis.getYYangmingpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details11 = InfraredPdfAnalysis.getZTaiyinpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details12 = InfraredPdfAnalysis.getYTaiyinpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details13 = InfraredPdfAnalysis.getZShaoyinpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details14 = InfraredPdfAnalysis.getYShaoyinpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details15 = InfraredPdfAnalysis.getZJueyinpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> details16 = InfraredPdfAnalysis.getYJueyinpibu(lines, txInfraredData.getId());
            List<TxInfraredDetails> saveList = Stream.of(details1, details2, details3, details4, details5, details6,
                            details7, details8, details9, details10, details11, details12, details13, details14,
                            details15, details16)
                    .flatMap(Collection::stream).collect(Collectors.toList());
            //新增详情数据
            txInfraredDetailsService.saveBatch(saveList);
            //保存文件中图片信息
            List<Map<String, Object>> imageList = InfraredPdfAnalysis.getImage(doc);
            imageList.stream().forEach(e -> {
                InputStream file = (InputStream) e.get("file");
                txInfraredImageService.saveTxInfraredImage(file, e.get("type").toString(),
                        OSSCatalogEnum.INFRARED_IMG.getRemark(), txInfraredData.getId());
            });

            //保存患者检查数据
            BuCheck buCheck=new BuCheck();
            buCheck.setPatientIdCard(infraredResult.getIDCard());
            buCheck.setType(CheckTypeEnum.INFRARED.getCode());
            buCheck.setMultimediaId(txInfraredData.getMultimediaId());
            buCheck.setCreateTime(txInfraredData.getTestDate());
            buCheckService.addCheck(buCheck);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(true);
    }

}
