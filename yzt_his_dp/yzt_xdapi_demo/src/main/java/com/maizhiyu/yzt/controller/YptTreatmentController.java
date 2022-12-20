package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.bean.aci.YptTreatmentCI;
import com.maizhiyu.yzt.bean.aro.YptTreatmentRO;
import com.maizhiyu.yzt.bean.avo.YptTreatmentVO;
import com.maizhiyu.yzt.entity.YptTreatment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptTreatmentService;
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
@Api(tags = "YPT治疗接口")
@RestController
@RequestMapping("/treatment")
public class YptTreatmentController {

    @Autowired
    private IYptTreatmentService service;


    @ApiOperation(value = "增加治疗", notes = "增加治疗")
    @PostMapping("/addTreatment")
    public Result<YptTreatmentVO.AddTreatmentVO> addTreatment(@RequestBody @Valid YptTreatmentRO.AddTreatmentRO ro) {
        YptTreatment treatment = YptTreatmentCI.INSTANCE.convert(ro);
        System.out.println(treatment);
        Integer res = service.addTreatment(treatment);
        YptTreatmentVO.GetTreatmentVO vo = YptTreatmentCI.INSTANCE.invertGetTreatmentVO(treatment);
        return Result.success(vo);
    }


    @ApiOperation(value = "删除治疗", notes = "删除治疗")
    @PostMapping("/delTreatment")
    public Result<Integer> delTreatment(@RequestBody @Valid YptTreatmentRO.DelTreatmentRO ro) {
        Integer res = service.delTreatment(ro.getId());
        return Result.success(res);
    }


    @ApiOperation(value = "修改治疗", notes = "修改治疗")
    @PostMapping("/setTreatment")
    public Result<Integer> setTreatment(@RequestBody @Valid YptTreatmentRO.SetTreatmentRO ro) {
        YptTreatment treatment = YptTreatmentCI.INSTANCE.convert(ro);
        Integer res = service.setTreatment(treatment);
        return Result.success(res);
    }


    @ApiOperation(value = "获取治疗", notes = "获取治疗")
    @PostMapping("/getTreatment")
    public Result<YptTreatmentVO.GetTreatmentVO> getTreatment(@RequestBody @Valid YptTreatmentRO.GetTreatmentRO ro) {
        YptTreatment treatment = service.getTreatment(ro.getId());
        YptTreatmentVO.GetTreatmentVO vo = YptTreatmentCI.INSTANCE.invertGetTreatmentVO(treatment);
        return Result.success(vo);
    }


    @ApiOperation(value = "获取治疗列表", notes = "获取治疗列表")
    @PostMapping("/getTreatmentList")
    public Result<IPage<YptTreatmentVO.GetTreatmentListVO>> getTreatmentList(@RequestBody @Valid YptTreatmentRO.GetTreatmentListRO ro) {
        Page<YptTreatment> list = service.getTreatmentList(new Page<YptTreatment>(ro.getPageNum(), ro.getPageSize()), ro.getTerm());
        Page<YptTreatmentVO.GetTreatmentListVO> pageInfo = YptTreatmentCI.INSTANCE.invertGetTreatmentListVO(list);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "上传HIS治疗", notes = "根据名称进行匹配csv[code编码,name名称,abbr缩写]")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "form")
    @PostMapping("/uploadTreatmentHis")
    public Result<YptTreatmentVO.UploadTreatmentHisVO> uploadTreatmentHis(@RequestPart MultipartFile file) {
        YptTreatmentVO.UploadTreatmentHisVO vo = new YptTreatmentVO.UploadTreatmentHisVO();
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
                    // 查找治疗
                    YptTreatment treatment = service.getTreatmentByName(name);
                    if (treatment != null) {
                        treatment.setAbbr(abbr);
                        treatment.setHiscode(code);
                        treatment.setHisname(name);
                        int res = service.setTreatment(treatment);
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
            log.info("治疗映射结果 成功:{}, 失败:{}, 错误:{}", vo.getSucceed(), vo.getFailed(), vo.getError());
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
