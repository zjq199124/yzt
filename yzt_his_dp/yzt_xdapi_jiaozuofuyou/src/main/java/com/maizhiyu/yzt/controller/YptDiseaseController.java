package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.bean.aci.YptDiseaseCI;
import com.maizhiyu.yzt.bean.aro.YptDiseaseRO;
import com.maizhiyu.yzt.bean.avo.YptDiseaseVO;
import com.maizhiyu.yzt.entity.YptDisease;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptDiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@Slf4j
@Api(tags = "YPT疾病接口")
@RestController
@RequestMapping("/disease")
public class YptDiseaseController {

    @Autowired
    private IYptDiseaseService service;


    @ApiOperation(value = "增加疾病", notes = "增加疾病")
    @PostMapping("/addDisease")
    public Result<YptDiseaseVO.AddDiseaseVO> addDisease(@RequestBody @Valid YptDiseaseRO.AddDiseaseRO ro) {
        YptDisease disease = YptDiseaseCI.INSTANCE.convert(ro);
        System.out.println(disease);
        Integer res = service.addDisease(disease);
        YptDiseaseVO.GetDiseaseVO vo = YptDiseaseCI.INSTANCE.invertGetDiseaseVO(disease);
        return Result.success(vo);
    }


    @ApiOperation(value = "删除疾病", notes = "删除疾病")
    @PostMapping("/delDisease")
    public Result<Integer> delDisease(@RequestBody @Valid YptDiseaseRO.DelDiseaseRO ro) {
        Integer res = service.delDisease(ro.getId());
        return Result.success(res);
    }


    @ApiOperation(value = "修改疾病", notes = "修改疾病")
    @PostMapping("/setDisease")
    public Result<Integer> setDisease(@RequestBody @Valid YptDiseaseRO.SetDiseaseRO ro) {
        YptDisease disease = YptDiseaseCI.INSTANCE.convert(ro);
        Integer res = service.setDisease(disease);
        return Result.success(res);
    }


    @ApiOperation(value = "获取疾病", notes = "获取疾病")
    @PostMapping("/getDisease")
    public Result<YptDiseaseVO.GetDiseaseVO> getDisease(@RequestBody @Valid YptDiseaseRO.GetDiseaseRO ro) {
        YptDisease disease = service.getDisease(ro.getId());
        YptDiseaseVO.GetDiseaseVO vo = YptDiseaseCI.INSTANCE.invertGetDiseaseVO(disease);
        return Result.success(vo);
    }


    @ApiOperation(value = "获取疾病列表", notes = "获取疾病列表")
    @PostMapping("/getDiseaseList")
    public Result<IPage<YptDiseaseVO.GetDiseaseListVO>> getDiseaseList(@RequestBody @Valid YptDiseaseRO.GetDiseaseListRO ro) {
        Page page = new Page(ro.getPageNum(), ro.getPageSize());
        Page<YptDisease> list = service.getDiseaseList(page, ro.getTerm());
        Page<YptDiseaseVO.GetDiseaseListVO> pageInfo = YptDiseaseCI.INSTANCE.invertGetDiseaseListVO(list);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "上传HIS疾病", notes = "根据名称进行匹配csv[code编码,name名称,abbr缩写]")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType="MultipartFile", paramType = "form")
    @PostMapping("/uploadDiseaseHis")
    public Result<YptDiseaseVO.UploadDiseaseHisVO> uploadDiseaseHis(@RequestPart MultipartFile file) {
        YptDiseaseVO.UploadDiseaseHisVO vo = new YptDiseaseVO.UploadDiseaseHisVO();
        // 文件变量
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 生成Reader
            inputStream = file.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            // 生成Parser
            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);
            // 处理数据
            for (CSVRecord record : parser.getRecords()) {
                // 判断字段长度
                if (record.size() >= 3) {
                    // 解析字段
                    String code = record.get(0).trim();
                    String name = record.get(1).trim();
                    String abbr = record.get(2).trim();
                    // 查找疾病
                    YptDisease disease = service.getDiseaseByHisname(name);
                    if (disease != null) {
                        disease.setAbbr(abbr);
                        disease.setHiscode(code);
                        disease.setHisname(name);
                        int res = service.setDisease(disease);
                        if (res == 1) {
                            vo.succeedPlus();
                            vo.getSucceedList().add(code + "-" + name);
                        } else {
                            vo.failedPlus();
                            vo.getFailedList().add(code + "-" + name);
                        }
                    } else {
                        vo.failedPlus();
                        vo.getFailedList().add(code + "-" + name);
                    }
                } else {
                    vo.errorPlus();
                    vo.getErrorList().add(record.toString());
                }
            }
            log.info("疾病映射结果 成功:{}, 失败:{}, 错误:{}", vo.getSucceed(), vo.getFailed(), vo.getError());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success(vo);
    }
}
