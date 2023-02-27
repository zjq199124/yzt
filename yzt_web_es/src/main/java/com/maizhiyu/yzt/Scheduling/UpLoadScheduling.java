package com.maizhiyu.yzt.Scheduling;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.ObjectUtil;
import com.maizhiyu.yzt.controller.TeHwcxController;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.TxInfraredData;
import com.maizhiyu.yzt.entity.TxInfraredDetails;
import com.maizhiyu.yzt.enums.CheckTypeEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.enums.UpLoadType;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.TxInfraredDataService;
import com.maizhiyu.yzt.service.TxInfraredDetailsService;
import com.maizhiyu.yzt.service.TxInfraredImageService;
import com.maizhiyu.yzt.serviceimpl.BuCheckService;
import com.maizhiyu.yzt.utils.RedisUtils;
import com.maizhiyu.yzt.utils.infrared.InfraredPdfAnalysis;
import com.maizhiyu.yzt.utils.infrared.InfraredResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
* 定时上传数据任务类
* @auther zjq
* @date 2023-02-27
* */
@Component
@Slf4j
public class UpLoadScheduling {

    @Autowired
    TxInfraredDataService txInfraredDataService;

    @Autowired
    TxInfraredDetailsService txInfraredDetailsService;

    @Autowired
    TxInfraredImageService txInfraredImageService;

    @Autowired
    BuCheckService buCheckService;

    @Autowired
    RedisUtils redisUtils;

    //每天凌晨2点钟执行定时任务上传文件
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(cron = "0/10 * * * * ? ")
    public Result<Boolean> upLoadScheduling ()throws IOException {
        List<String> list = (ArrayList) redisUtils.get(UpLoadType.NO.getCode());
        if (ObjectUtil.isNotEmpty(list)) {
            InfraredResult infraredResult = new InfraredResult();
            for (String tcmUrl : list) {
                infraredResult = (InfraredResult) redisUtils.get(tcmUrl);
                if (ObjectUtil.isNotEmpty(infraredResult)) {
                    byte[] dataBytes = getBytes(new URL(infraredResult.getTcmUrl()).openStream());
                    //读取的文件流
                    InputStream inputStream = new ByteArrayInputStream(dataBytes);
                    //上传的文件流
                    InputStream inputStreamUpLoad = new ByteArrayInputStream(dataBytes);
                    //获取文件
                    PDDocument doc = PDDocument.load(inputStream);
                    //获取一个PDFTextStripper文本剥离对象
                    PDFTextStripper textStripper = new PDFTextStripper();
                    textStripper.setStartPage(3);
                    textStripper.setEndPage(14);
                    String content = textStripper.getText(doc);
//                log.info("内容:" + content);
                    String[] lines = content.split("[\\t\\n]+");
                    //先保存检测数据
                    TxInfraredData txInfraredData = InfraredPdfAnalysis.getThwbase(lines);
                    txInfraredData.setPhone(infraredResult.getMobile());
                    txInfraredData.setIdCard(infraredResult.getIdCard());
                    txInfraredData.setTestDate(infraredResult.getCreateTime());
                    String[] paths = infraredResult.getTcmUrl().split("/");
                    String fileName = paths[paths.length - 1];
                    //开启pdf线程上传
                    UpLoadTread upLoadTread = new UpLoadTread(txInfraredData, inputStreamUpLoad, fileName);
                    // pdf上传线程执行结果
                    FutureTask<TxInfraredData> futureTask = new FutureTask(upLoadTread);
                    //创建Thread对象，并调用start()
                    new Thread(futureTask).start();
                    try {
                        txInfraredDataService.save(futureTask.get());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
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
                    //开启图片线程上传
                    UpLoadImgTread upLoadImgTread = new UpLoadImgTread(imageList, txInfraredData.getId());
                    // pdf上传线程执行结果
                    FutureTask<TxInfraredData> futureTaskImg = new FutureTask(upLoadImgTread);
                    //创建Thread对象，并调用start()
                    new Thread(futureTaskImg).start();
                    //保存患者检查数据
                    BuCheck buCheck = new BuCheck();
                    buCheck.setIdCard(infraredResult.getIdCard());
                    buCheck.setMobile(infraredResult.getMobile());
                    buCheck.setType(CheckTypeEnum.INFRARED.getCode());
                    buCheck.setMultimediaId(txInfraredData.getMultimediaId());
                    buCheck.setCreateTime(txInfraredData.getTestDate());
                    buCheckService.addCheck(buCheck);
                    redisUtils.del(tcmUrl);
                    return Result.success(true);
                }
                redisUtils.del(UpLoadType.NO.getCode());
            }
        }
        return Result.success();
    }

    //创建一个实现Callable的实现类
    class UpLoadTread implements Callable {
        private TxInfraredData txInfraredData;
        private InputStream openStream;
        private String fileName;
        UpLoadTread(TxInfraredData txInfraredData,InputStream openStream,String fileName){
            this.txInfraredData=txInfraredData;
            this.openStream = openStream;
            this.fileName = fileName;
        }
        //实现call方法
        @Override
        public TxInfraredData call() throws Exception {
//            System.out.println("pdf开始异步上传 " +  txInfraredData );
            try {
                txInfraredDataService.saveTxInfrareData(txInfraredData, openStream, fileName);
            }catch (Exception e) {
                throw new BusinessException("pdf上传失败");
            }
            return txInfraredData;
        }
    }

    class UpLoadImgTread implements Callable {
        private List<Map<String, Object>> imageList;
        private Long infraredDataId;
        UpLoadImgTread(List<Map<String, Object>> imageList,Long infraredDataId ){
            this.imageList=imageList;
            this.infraredDataId = infraredDataId;
        }
        //实现call方法
        @Override
        public Result call() throws Exception {
//            System.out.println("图片开始异步上传 " +  imageList );
            try {
                imageList.stream().forEach(e -> {
                    InputStream file = (InputStream) e.get("file");
                    txInfraredImageService.saveTxInfraredImage(file, e.get("type").toString(),
                            OSSCatalogEnum.INFRARED_IMG.getRemark(), infraredDataId);
                });
            }catch (Exception e) {
                throw new BusinessException("图片上传失败");
            }
            return Result.success();
        }
    }

    /**
     * 把InputStream首先转换成byte[].
     * @param source
     * @return
     * @throws IOException
     */
    protected byte[] getBytes(InputStream source) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = source.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        return baos.toByteArray();
    }

}
