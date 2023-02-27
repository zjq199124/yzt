package com.maizhiyu.yzt.controller;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.ObjectUtil;
import com.maizhiyu.yzt.enums.UpLoadType;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.enums.CheckTypeEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.TxInfraredDataService;
import com.maizhiyu.yzt.service.TxInfraredDetailsService;
import com.maizhiyu.yzt.service.TxInfraredImageService;
import com.maizhiyu.yzt.serviceimpl.BuCheckService;
import com.maizhiyu.yzt.utils.RedisUtils;
import com.maizhiyu.yzt.utils.infrared.InfraredPdfAnalysis;
import com.maizhiyu.yzt.utils.infrared.InfraredResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "红外成像设备接口")
@RestController
@RequestMapping("/hwcx")
@Slf4j
public class TeHwcxController implements Serializable {

//    @Resource
//    TxInfraredDataService txInfraredDataService;
//
//    @Resource
//    TxInfraredDetailsService txInfraredDetailsService;
//
//    @Resource
//    TxInfraredImageService txInfraredImageService;
//
//    @Resource
//    BuCheckService buCheckService;

    @Autowired
    RedisUtils redisUtils;
    @ApiOperation(value = "接收红外检测报告", notes = "接收红外检测报告")
    @ApiImplicitParams({})
    @PostMapping(value = "/receiveInfrared")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> receiveInfrared (@Valid @RequestBody InfraredResult infraredResult)  throws Exception  {
        if(ObjectUtil.isNotEmpty(infraredResult.getTcmUrl())){
            //获取文件地址
            String tcmUrl =  infraredResult.getTcmUrl().split("\\/")[4].split("\\.")[0];
            Map map = new HashMap();
            List list =  new ArrayList();
            //判断redis中是否有未上传的数据
            if (redisUtils.hasKey(UpLoadType.NO.getCode())){
                list =  (ArrayList)redisUtils.get(UpLoadType.NO.getCode());
                if (!list.contains(tcmUrl)){
                    list.add(tcmUrl);
                    redisUtils.del(UpLoadType.NO.getCode());
                    redisUtils.set(UpLoadType.NO.getCode(),list);
                    redisUtils.set(tcmUrl,infraredResult);
                }
            }else{
                //第一次数据加入
                list.add(tcmUrl);
                redisUtils.set(UpLoadType.NO.getCode(),list);
                redisUtils.set(tcmUrl,infraredResult);
            }
            log.info("测试"+redisUtils.get(UpLoadType.NO.getCode()));
        }else {
            return Result.failure("上传失败，pdf文件缺失");
        }
        return Result.success();
    };


//    //TODO 文件分析数据保存
//    @ApiOperation(value = "接收红外检测报告", notes = "接收红外检测报告")
//    @ApiImplicitParams({})
//    @PostMapping(value = "/receiveInfrared")
//    @Transactional(rollbackFor = Exception.class)
//    public Result<Boolean> receiveInfrared (@Valid @RequestBody InfraredResult infraredResult)throws IOException  {
////       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////       System.out.println("top:"+sdf.format(System.currentTimeMillis()));
//            log.info("接收红外检测报告："+ SystemClock.nowDate());
////        try {
////            InputStream inputStream = new URL(infraredResult.getTcmUrl()).openStream();
//            byte[] dataBytes = getBytes(new URL(infraredResult.getTcmUrl()).openStream());
//            //读取的文件流
//            InputStream inputStream = new ByteArrayInputStream(dataBytes);
//            //上传的文件流
//            InputStream inputStreamUpLoad = new ByteArrayInputStream(dataBytes);
//            //获取文件
//            PDDocument doc = PDDocument.load(inputStream);
//            //获取一个PDFTextStripper文本剥离对象
//            PDFTextStripper textStripper = new PDFTextStripper();
//            textStripper.setStartPage(3);
//            textStripper.setEndPage(14);
//            String content = textStripper.getText(doc);
//           log.info("内容:" + content);
////            log.info("全部页数" + doc.getNumberOfPages());
//            String[] lines = content.split("[\\t\\n]+");
//            //先保存检测数据
////        System.out.println("start:"+sdf.format(System.currentTimeMillis()));
//            TxInfraredData txInfraredData = InfraredPdfAnalysis.getThwbase(lines);
//            txInfraredData.setPhone(infraredResult.getMobile());
//            txInfraredData.setIdCard(infraredResult.getIdCard());
//            txInfraredData.setTestDate(infraredResult.getCreateTime());
//            String[] paths = infraredResult.getTcmUrl().split("/");
//            String fileName = paths[paths.length - 1];
////        System.out.println("middle:"+sdf.format(System.currentTimeMillis()));
////        txInfraredDataService.saveTxInfrareData(txInfraredData, new URL(infraredResult.getTcmUrl()).openStream(), fileName);
//        //开启pdf线程上传
//        UpLoadTread upLoadTread = new UpLoadTread(txInfraredData,inputStreamUpLoad,fileName);
//        // pdf上传线程执行结果
//        FutureTask<TxInfraredData> futureTask = new FutureTask(upLoadTread);
//        //创建Thread对象，并调用start()
//        new Thread(futureTask).start();
//
//
//        try {
//            txInfraredDataService.save(futureTask.get());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//
////        System.out.println("end:"+sdf.format(System.currentTimeMillis()));
//            //保存数据详情
//
//            List<TxInfraredDetails> details1 = InfraredPdfAnalysis.getZangfu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details2 = InfraredPdfAnalysis.getSanjiao(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details3 = InfraredPdfAnalysis.getZJiepaofenqu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details4 = InfraredPdfAnalysis.getBJiepaofenqu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details5 = InfraredPdfAnalysis.getZYJiepaofenqu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details6 = InfraredPdfAnalysis.getZShaoyangpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details7 = InfraredPdfAnalysis.getYShaoyangpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details8 = InfraredPdfAnalysis.getZTaiyangpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details9 = InfraredPdfAnalysis.getZYangmingpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details10 = InfraredPdfAnalysis.getYYangmingpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details11 = InfraredPdfAnalysis.getZTaiyinpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details12 = InfraredPdfAnalysis.getYTaiyinpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details13 = InfraredPdfAnalysis.getZShaoyinpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details14 = InfraredPdfAnalysis.getYShaoyinpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details15 = InfraredPdfAnalysis.getZJueyinpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> details16 = InfraredPdfAnalysis.getYJueyinpibu(lines, txInfraredData.getId());
//            List<TxInfraredDetails> saveList = Stream.of(details1, details2, details3, details4, details5, details6,
//                            details7, details8, details9, details10, details11, details12, details13, details14,
//                            details15, details16)
//                    .flatMap(Collection::stream).collect(Collectors.toList());
//            //新增详情数据
////        System.out.println("end1:"+sdf.format(System.currentTimeMillis()));
//            txInfraredDetailsService.saveBatch(saveList);
////        System.out.println("end2:"+sdf.format(System.currentTimeMillis()));
//            //保存文件中图片信息
//            List<Map<String, Object>> imageList = InfraredPdfAnalysis.getImage(doc);
////        System.out.println("end3:"+sdf.format(System.currentTimeMillis()));
//
//
//        //开启图片线程上传
//        UpLoadImgTread upLoadImgTread = new UpLoadImgTread(imageList,txInfraredData.getId());
//        // pdf上传线程执行结果
//        FutureTask<TxInfraredData> futureTaskImg = new FutureTask(upLoadImgTread);
//        //创建Thread对象，并调用start()
//        new Thread(futureTaskImg).start();
//
////        imageList.stream().forEach(e -> {
////                InputStream file = (InputStream) e.get("file");
////                txInfraredImageService.saveTxInfraredImage(file, e.get("type").toString(),
////                        OSSCatalogEnum.INFRARED_IMG.getRemark(), txInfraredData.getId());
////        });
////        System.out.println("end4:"+sdf.format(System.currentTimeMillis()));
//            //保存患者检查数据
//            BuCheck buCheck = new BuCheck();
//            buCheck.setIdCard(infraredResult.getIdCard());
//            buCheck.setMobile(infraredResult.getMobile());
//            buCheck.setType(CheckTypeEnum.INFRARED.getCode());
//            buCheck.setMultimediaId(txInfraredData.getMultimediaId());
//            buCheck.setCreateTime(txInfraredData.getTestDate());
//            buCheckService.addCheck(buCheck);
////        System.out.println("last:"+sdf.format(System.currentTimeMillis()));
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        return Result.success(true);
//    }
//
//    //创建一个实现Callable的实现类
//    class UpLoadTread implements Callable {
//        private TxInfraredData txInfraredData;
//        private InputStream openStream;
//        private String fileName;
//        UpLoadTread(TxInfraredData txInfraredData,InputStream openStream,String fileName){
//            this.txInfraredData=txInfraredData;
//            this.openStream = openStream;
//            this.fileName = fileName;
//        }
//        //实现call方法
//        @Override
//        public TxInfraredData call() throws Exception {
////            System.out.println("pdf开始异步上传 " +  txInfraredData );
//            try {
//                txInfraredDataService.saveTxInfrareData(txInfraredData, openStream, fileName);
//            }catch (Exception e) {
//                throw new BusinessException("pdf上传失败");
//            }
//            return txInfraredData;
//        }
//    }
//
//    class UpLoadImgTread implements Callable {
//        private List<Map<String, Object>> imageList;
//        private Long infraredDataId;
//        UpLoadImgTread(List<Map<String, Object>> imageList,Long infraredDataId ){
//            this.imageList=imageList;
//            this.infraredDataId = infraredDataId;
//        }
//        //实现call方法
//        @Override
//        public Result call() throws Exception {
////            System.out.println("图片开始异步上传 " +  imageList );
//            try {
//                imageList.stream().forEach(e -> {
//                    InputStream file = (InputStream) e.get("file");
//                    txInfraredImageService.saveTxInfraredImage(file, e.get("type").toString(),
//                            OSSCatalogEnum.INFRARED_IMG.getRemark(), infraredDataId);
//                });
//            }catch (Exception e) {
//                throw new BusinessException("图片上传失败");
//            }
//            return Result.success();
//        }
//    }
//
//    /**
//     * 把InputStream首先转换成byte[].
//     * @param source
//     * @return
//     * @throws IOException
//     */
//    protected byte[] getBytes(InputStream source) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len;
//        while ((len = source.read(buffer)) > -1 ) {
//            baos.write(buffer, 0, len);
//        }
//        baos.flush();
//        return baos.toByteArray();
//    }

}
