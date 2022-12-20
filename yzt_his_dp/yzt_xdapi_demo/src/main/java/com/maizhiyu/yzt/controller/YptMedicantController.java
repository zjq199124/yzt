package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.bean.aci.YptMedicantCI;
import com.maizhiyu.yzt.bean.aro.YptMedicantRO;
import com.maizhiyu.yzt.bean.avo.YptMedicantVO;
import com.maizhiyu.yzt.entity.YptMedicant;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptMedicantService;
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
@Api(tags = "YPT药材接口")
@RestController
@RequestMapping("/medicant")
public class YptMedicantController {

    @Autowired
    private IYptMedicantService service;


    @ApiOperation(value = "增加药材", notes = "增加药材")
    @PostMapping("/addMedicant")
    public Result<YptMedicantVO.AddMedicantVO> addMedicant(@RequestBody @Valid YptMedicantRO.AddMedicantRO ro) {
        YptMedicant medicant = YptMedicantCI.INSTANCE.convert(ro);
        System.out.println(medicant);
        Integer res = service.addMedicant(medicant);
        YptMedicantVO.GetMedicantVO vo = YptMedicantCI.INSTANCE.invertGetMedicantVO(medicant);
        return Result.success(vo);
    }


    @ApiOperation(value = "删除药材", notes = "删除药材")
    @PostMapping("/delMedicant")
    public Result<Integer> delMedicant(@RequestBody @Valid YptMedicantRO.DelMedicantRO ro) {
        Integer res = service.delMedicant(ro.getId());
        return Result.success(res);
    }


    @ApiOperation(value = "修改药材", notes = "修改药材")
    @PostMapping("/setMedicant")
    public Result<Integer> setMedicant(@RequestBody @Valid YptMedicantRO.SetMedicantRO ro) {
        YptMedicant medicant = YptMedicantCI.INSTANCE.convert(ro);
        Integer res = service.setMedicant(medicant);
        return Result.success(res);
    }


    @ApiOperation(value = "获取药材", notes = "获取药材")
    @PostMapping("/getMedicant")
    public Result<YptMedicantVO.GetMedicantVO> getMedicant(@RequestBody @Valid YptMedicantRO.GetMedicantRO ro) {
        YptMedicant medicant = service.getMedicant(ro.getId());
        YptMedicantVO.GetMedicantVO vo = YptMedicantCI.INSTANCE.invertGetMedicantVO(medicant);
        return Result.success(vo);
    }


    @ApiOperation(value = "获取药材列表", notes = "获取药材列表")
    @PostMapping("/getMedicantList")
    public Result<IPage<YptMedicantVO.GetMedicantListVO>> getMedicantList(@RequestBody @Valid YptMedicantRO.GetMedicantListRO ro) {
        Page<YptMedicant> list = service.getMedicantList(new Page(ro.getPageNum(), ro.getPageSize()), ro.getTerm());
        Page<YptMedicantVO.GetMedicantListVO> pageInfo = YptMedicantCI.INSTANCE.invertGetMedicantListVO(list);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "上传HIS药材", notes = "根据名称进行匹配csv[code编码,name名称,abbr缩写]")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "form")
    @PostMapping("/uploadMedicantHis")
    public Result<YptMedicantVO.UploadMedicantHisVO> uploadMedicantHis(@RequestPart MultipartFile file) {
        YptMedicantVO.UploadMedicantHisVO vo = new YptMedicantVO.UploadMedicantHisVO();
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
                    // 查找药材
                    YptMedicant medicant = service.getMedicantByName(name);
                    if (medicant != null) {
                        medicant.setAbbr(abbr);
                        medicant.setHiscode(code);
                        medicant.setHisname(name);
                        int res = service.setMedicant(medicant);
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
            log.info("药材映射结果 成功:{}, 失败:{}, 错误:{}", vo.getSucceed(), vo.getFailed(), vo.getError());
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
