package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.maizhiyu.yzt.entity.BuSmsRecord;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.service.IBuSmsRecordService;
import com.maizhiyu.yzt.service.ISmsService;
import com.maizhiyu.yzt.utils.sms.SendSmsUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SmsServiceImpl implements ISmsService {

    @Value("${sms.signName}")
    private String signName;

    @Resource
    private SendSmsUtil sendSmsUtil;

    @Resource
    private IBuSmsRecordService buSmsRecordService;

    @Override
    public Boolean sendSms(String templateCode, String phone, Map<String, String> map) {
        String result = null;
        try {
            result = sendSmsUtil.sendSms(signName, SmsTemplateEnum.VERIFICATION_CODE.getCode(), phone, map);

            BuSmsRecord buSmsRecord = new BuSmsRecord();
            buSmsRecord.setPhone(phone);
            buSmsRecord.setTemplateCode(templateCode);
            buSmsRecord.setReqParams(new Gson().toJson(map));
            buSmsRecord.setResParams(result);
            buSmsRecord.setCreateTime(new Date());

            JSONObject resultJson = JSONObject.parseObject(result);
            buSmsRecord.setResult("OK".equals(resultJson.get("code").toString()) ? 1 : 0);
            buSmsRecord.setRequestId(resultJson.get("requestId").toString());
            buSmsRecordService.save(buSmsRecord);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信验证码失败 "  + e.getMessage());
        }
        return false;
    }
}
