package com.maizhiyu.yzt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeEquipService;
import com.maizhiyu.yzt.service.ITeMaintainService;
import com.maizhiyu.yzt.service.ITeWarnService;
import com.maizhiyu.yzt.service.ITxXzcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Api(tags = "熏蒸床接口")
@RestController
@RequestMapping("/xzc")
public class TeXzcController {

    @Autowired
    private ITeEquipService equipService;

    @Autowired
    private ITxXzcService xzcService;

    @Autowired
    private ITeWarnService warnService;

    @Autowired
    private ITeMaintainService maintainService;


    @ApiOperation(value = "数据接口", notes = "数据接口")
    @ApiImplicitParams({})
    @PostMapping(value = "/data", consumes = "application/octet-stream")
    public Result data(@RequestBody String string) {
        System.out.println("##### " + string);
        try {
            JSONObject jsonObject = JSON.parseObject(string);
            String type = jsonObject.getString("Type");
            if (type == null) {
                throw new BusinessException("格式错误：缺少Type字段");
            }
            switch (type) {
                case "1":
                    processOpen(jsonObject);
                    break;
                case "2":
                    processStart(jsonObject);
                    break;
                case "3":
                    JSONObject object = processHeart(jsonObject);
                    return Result.success(object);
                case "4":
                    processData(jsonObject);
                    break;
                case "5":
                    processClose(jsonObject);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.failure(40001, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(40001, e.getMessage());
        }
        return Result.success();
    }


    private void processOpen(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("DeviceID");
        Date date = jsonObject.getDate("Date");
        Integer washTimes = jsonObject.getInteger("Wash");
        Integer sprayTimes = jsonObject.getInteger("Spray");
        Integer sysState = jsonObject.getInteger("SysState");

        // 更新设备状态
        doUpdateState(code, sysState);

        // 处理维护数据
        if (washTimes != null && washTimes > 0) {
            TeMaintain maintain = new TeMaintain();
            maintain.setCode(code);
            maintain.setType(1);
            maintain.setNumber(washTimes);
            maintain.setTime(date);
            Integer res = maintainService.addMaintain(maintain);
        }
        if (sprayTimes != null && sprayTimes > 0) {
            TeMaintain maintain = new TeMaintain();
            maintain.setCode(code);
            maintain.setType(2);
            maintain.setNumber(sprayTimes);
            maintain.setTime(date);
            Integer res = maintainService.addMaintain(maintain);
        }
    }

    private void processStart(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("DeviceID");
        String runId = jsonObject.getString("RunID");
        Integer state = jsonObject.getInteger("SysState");

        Integer minute = jsonObject.getInteger("TimeMin");
        Integer second = jsonObject.getInteger("TimeSec");

        Integer neckSetTemp = jsonObject.getInteger("NSetTemper");
        Integer neckWaterTemp = jsonObject.getInteger("NWaterTemper");
        Integer neckSteamTemp = jsonObject.getInteger("NSteamTemper");

        Integer waistSetTemp = jsonObject.getInteger("NSetTemper");
        Integer waistWaterTemp = jsonObject.getInteger("NWaterTemper");
        Integer waistSteamTemp = jsonObject.getInteger("NSteamTemper");

        // 更新设备状态
        doUpdateState(code, state);

        // 计算运行时间
        // TODO
        Integer duration = minute * 60 + second;

        // 处理运行设置
        TxXzcRun run = new TxXzcRun();
        run.setCode(code);
        run.setRunid(runId);
        run.setStatus(state);
        run.setDuration(duration);
        run.setNeckTemp(neckSetTemp);
        run.setWaistTemp(waistSetTemp);
        run.setStartTime(new Date());
        Integer res = xzcService.addRun(run);
    }

    private JSONObject processHeart(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("DeviceID");
        String runId = jsonObject.getString("RunID");
        String errorId = jsonObject.getString("ErrorID");
        Integer type = Integer.parseInt(errorId);

        // 更新设备状态
        doUpdateState(code, 2);

        // 处理报警信息
        if (type > 0) {
            TeWarn warn = new TeWarn();
            warn.setCode(code);
            warn.setRunid(runId);
            warn.setType(type);
            warn.setTime(new Date());
            warnService.addWarn(warn);
            // 1颈部体感超温 2颈部药液超温 3颈部液位过低  4腰部体感超温 5腰部药液超温 6腰部液位过低 7颈腰部体感超温 8颈腰部药液超温 9颈腰部液位过低
        }

        // 生成返回数据
        JSONObject object = new JSONObject();
        object.put("Type", 3);
        object.put("DeviceId", code);
        object.put("RunID", runId);

        // 查询回传命令
        Integer status = 1;
        TxXzcCmd cmd = xzcService.getCmd(null, code, runId, status);

        // 处理回传命令
        if (cmd != null) {
            // 修改命令状态
            cmd.setStatus(2);
            cmd.setExecuteTime(new Date());
            xzcService.setCmd(cmd);
            // 不同命令处理
            switch (cmd.getCmd()) {
                case 1: // 开机
                case 2: // 关机
                case 3: // 开始
                case 4: // 暂停
                case 5: // 继续
                case 7: // 结束
                case 6: // 设置（所有的操作都用此命令来实现）
                    Integer sysState = cmd.getSysStatus();
                    Double nSetTemper = cmd.getNeckTemp().doubleValue() * 10;
                    Double wSetTemper = cmd.getWaistTemp().doubleValue() * 10;
                    if (sysState != null) {
                        object.put("SysState", sysState);
                    }
                    if (nSetTemper != null) {
                        object.put("NSetTemper", nSetTemper);
                    }
                    if (wSetTemper != null) {
                        object.put("WSetTemper", wSetTemper);
                    }
                    break;
            }
        }
        return object;
    }

    private void processData(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("DeviceID");
        String runId = jsonObject.getString("RunID");
        String state = jsonObject.getString("SysState");

        Integer minute = jsonObject.getInteger("TimeMin");
        Integer second = jsonObject.getInteger("TimeSec");

        Integer neckSetTemp = jsonObject.getInteger("NSetTemper");
        Integer neckWaterTemp = jsonObject.getInteger("NWaterTemper");
        Integer neckSteamTemp = jsonObject.getInteger("NSteamTemper");

        Integer waistSetTemp = jsonObject.getInteger("NSetTemper");
        Integer waistWaterTemp = jsonObject.getInteger("NWaterTemper");
        Integer waistSteamTemp = jsonObject.getInteger("NSteamTemper");

        // 处理运行数据
        TxXzcData data = new TxXzcData();
        data.setCode(code);
        data.setRunid(runId);
        data.setTime(new Date());
        data.setNeckLiquidTemp(neckWaterTemp);
        data.setNeckSkinTemp(neckSteamTemp);
        data.setWaistLiquidTemp(waistWaterTemp);
        data.setWaistSkinTemp(waistSteamTemp);
        xzcService.addData(data);
    }

    private void processClose(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("DeviceID");
        String runId = jsonObject.getString("RunID");
        Integer state = jsonObject.getInteger("SysState");

        // 更新设备状态
        doUpdateState(code, state);
    }

    private void doUpdateState(String code, Integer state) {
        TeEquip equip = new TeEquip();
        equip.setCode(code);
        equip.setState(1);
        equip.setHeartTime(new Date());
        equipService.setEquip(equip);
    }

}
