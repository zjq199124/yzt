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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
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
            String type = jsonObject.getString("T");
            if (type == null) {
                throw new BusinessException("格式错误：缺少T字段");
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
                    System.out.println("@@@@@ heart return : " + object);
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
        String code = jsonObject.getString("D");
        Integer washTimes = jsonObject.getInteger("K");
        Integer sprayTimes = jsonObject.getInteger("L");

        // 解析时间
        Date date = null;
        try {
            date = DateUtils.parseDate(jsonObject.getString("H"), "yyMMdd");
        } catch (Exception e) {
            date = null;
        }

        // 更新设备状态(预热状态)
        doUpdateState(code, 1);

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
        String code = jsonObject.getString("D");
        String runId = jsonObject.getString("R");
        Integer state = jsonObject.getInteger("S");

        Integer minute = jsonObject.getInteger("M");
        Integer second = jsonObject.getInteger("N");

        Double neckSetTemp = jsonObject.getDouble("O");
        Double neckWaterTemp = jsonObject.getDouble("P");
        Double neckSteamTemp = jsonObject.getDouble("Q");

        Double waistSetTemp = jsonObject.getDouble("X");
        Double waistWaterTemp = jsonObject.getDouble("Y");
        Double waistSteamTemp = jsonObject.getDouble("Z");

        // 更新设备状态
        doUpdateState(code, state);

        // 计算运行时间
        // TODO
        Integer duration = minute;

        // 处理运行设置
        TxXzcRun run = new TxXzcRun();
        run.setCode(code);
        run.setRunid(runId);
        run.setStatus(state);
        run.setDuration(duration);
        run.setNeckTemp(neckSetTemp / 10);
        run.setWaistTemp(waistSetTemp / 10);
        run.setStartTime(new Date());
        Integer res = xzcService.addRun(run);
    }

    private JSONObject processHeart(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("D");
        String runId = jsonObject.getString("R");
        String errorId = jsonObject.getString("E");
        Integer errorType = Integer.parseInt(errorId);
        Integer neckSetTemp = jsonObject.getInteger("O");
        Integer waistSetTemp = jsonObject.getInteger("X");

        // 更新设备状态
        doUpdateState(code, 2);

        // 更新运行温度
        if (neckSetTemp != null && waistSetTemp != null) {
            TxXzcRun run = new TxXzcRun();
            run.setCode(code);
            run.setRunid(String.valueOf(Integer.parseInt(runId)));
            run.setNeckTemp(neckSetTemp.doubleValue() / 10);
            run.setWaistTemp(waistSetTemp.doubleValue() / 10);
            xzcService.setRunOnly(run);
        }

        // 处理报警信息
        if (errorType > 0) {
            // 添加预警记录
            TeWarn warn = new TeWarn();
            warn.setCode(code);
            warn.setRunid(runId);
            warn.setType(errorType);
            warn.setTime(new Date());
            warnService.addWarn(warn);
            // 更新运行预警
            TxXzcRun run = new TxXzcRun();
            run.setCode(code);
            run.setRunid(runId);
            xzcService.setRunWarn(run);
        }

        // 生成返回数据
        JSONObject object = null;

        // 查询回传命令
        Integer status = 1;
        TxXzcCmd cmd = xzcService.getCmd(null, code, runId, status);
        System.out.println("$$$$$ " + cmd);

        // 处理回传命令
        if (cmd != null) {
            // 设置基础数据
            object = new JSONObject();
            // 为了减少传输字节数暂时去掉这三项
            // object.put("T", 3);
            // object.put("D", code);
            // object.put("R", runId);
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
                    if (cmd.getSysState() != null) {
                        Integer sysState = cmd.getSysState();
                        object.put("S", sysState);
                    }
                    if (cmd.getNeckTemp() != null) {
                        Integer nSetTemper = cmd.getNeckTemp().intValue() * 10;
                        object.put("O", nSetTemper);
                    }
                    if (cmd.getWaistTemp() != null) {
                        Integer wSetTemper = cmd.getWaistTemp().intValue() * 10;
                        object.put("X", wSetTemper);
                    }
                    break;
            }
        }
        return object;
    }

    private void processData(JSONObject jsonObject) {
        // 解析数据字段
        String code = jsonObject.getString("D");
        String runId = jsonObject.getString("R");
        String state = jsonObject.getString("S");

        Integer minute = jsonObject.getInteger("M");
        Integer second = jsonObject.getInteger("N");

        Integer neckSetTemp = jsonObject.getInteger("O");
        Integer neckWaterTemp = jsonObject.getInteger("P");
        Integer neckSteamTemp = jsonObject.getInteger("Q");

        Integer waistSetTemp = jsonObject.getInteger("X");
        Integer waistWaterTemp = jsonObject.getInteger("Y");
        Integer waistSteamTemp = jsonObject.getInteger("Z");

        // 更新运行状态
        TxXzcRun run = new TxXzcRun();
        run.setCode(code);
        run.setRunid(String.valueOf(Integer.parseInt(runId)));
        run.setNeckTemp(neckSetTemp.doubleValue() / 10);
        run.setWaistTemp(waistSetTemp.doubleValue() / 10);
        xzcService.setRunOnly(run);

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
        String code = jsonObject.getString("D");
        String runId = jsonObject.getString("R");
        Integer state = jsonObject.getInteger("S");

        // 更新运行数据
        TxXzcRun run = new TxXzcRun();
        run.setCode(code);
        run.setRunid(runId);
        run.setStatus(4);
        run.setEndTime(new Date());
        xzcService.setRunOnly(run);

        // 更新设备状态
        doUpdateState(code, state);
    }

    private void doUpdateState(String code, Integer state) {
        TeEquip equip = new TeEquip();
        equip.setCode(code);
        equip.setState(state);
        equip.setHeartTime(new Date());
        equipService.setEquip(equip);
    }

    public static void main(String[] args) throws ParseException {
        JSONObject jsonObject = JSON.parseObject("{\"H\":\"220515\"}");
        String datestr = jsonObject.getString("H");
        Date date = DateUtils.parseDate(datestr, "yyMMdd");
        System.out.println(date);
    }

}
