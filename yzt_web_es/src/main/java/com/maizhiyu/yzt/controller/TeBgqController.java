package com.maizhiyu.yzt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maizhiyu.yzt.entity.TxBgqRun;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITxBgqRunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@Api(tags = "拔罐器接口")
@RestController
@RequestMapping("/bgq")
public class TeBgqController {

    @Autowired
    ITxBgqRunService service;

    @ApiOperation(value = "数据接口", notes = "数据接口", consumes = "applictaion/octet-stream")
    @ApiImplicitParams({})
    @PostMapping(value = "/data", consumes = "application/octet-stream")
    public Result data(@RequestBody String string) {
        log.info(" %%%%% " + string);
        try {
            JSONObject jsonObject = JSON.parseObject(string);
            Integer type = jsonObject.getInteger("T");
            if (type == null) {
                throw new BusinessException("格式错误：缺少T字段");
            }
            switch (type) {
                case 1:
                    break;
                case 2:
                    processStart(jsonObject);
                    break;
                case 3:
                    processStop(jsonObject);
                    break;

            }
        } catch (Exception e) {
            log.error("处理拔罐器数据异常：" + e.getMessage());
        }
        return Result.success();
    }

    private void processStart(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("D");
        String runid = jsonObject.getString("R");
        Integer status = jsonObject.getInteger("S");
        Integer mode = jsonObject.getInteger("W");
        Integer pressure = jsonObject.getInteger("P");
        Integer duration = jsonObject.getInteger("M");
        // 生成实体
        TxBgqRun run = new TxBgqRun();
        run.setCode(code);
        run.setRunid(runid);
        run.setStatus(status);
        run.setMode(mode);
        run.setPressure(pressure);
        run.setDuration(duration);
        run.setStartTime(new Date());
        // 保存数据
        int res = service.addBgqRun(run);
        System.out.println(res);
    }

    private void processStop(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("D");
        String runid = jsonObject.getString("R");
        Integer status = jsonObject.getInteger("S");
        // Integer mode = jsonObject.getInteger("W");
        // Integer pressure = jsonObject.getInteger("P");
        // Integer duration = jsonObject.getInteger("M");
        // 生成实体
        TxBgqRun run = new TxBgqRun();
        run.setCode(code);
        run.setRunid(runid);
        run.setStatus(status);
        run.setEndTime(new Date());
        // 保存数据
        service.setBgqRun(run);
    }

}
