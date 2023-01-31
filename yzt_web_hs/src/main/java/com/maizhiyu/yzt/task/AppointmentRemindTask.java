package com.maizhiyu.yzt.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentService;
import com.maizhiyu.yzt.service.ISmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
@Slf4j
public class AppointmentRemindTask {

    @Value("${sms.signName}")
    private String signName;

    @Resource
    private IBuPrescriptionItemAppointmentItemService buPrescriptionItemAppointmentItemService;

    @Resource
    private ISmsService smsService;

    //当天18:00提醒及时最近一次预约时间及查看方式
    @Scheduled(cron = "0 0 18 * * ?")
    private void RemindLatestAppointmentTask() {
        //查询当天创建的预约数据
        Date startDate = DateUtil.beginOfDay(new Date());
        List<BuPrescriptionItemAppointmentItem> list = buPrescriptionItemAppointmentItemService.selectRemindLatestAppointmentList(startDate, new Date());
        Map<String, String> map = new HashMap<>();
        map.put("code", "123456");
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                smsService.sendSms(signName,SmsTemplateEnum.VERIFICATION_CODE.getCode(), item.getPhone(), map);
            });
        }
    }

    //当天18:00提醒及时最近一次预约时间及查看方式
    @Scheduled(cron = "0 0 18 * * ?")
    private void TreatmentRemindTask() {
        //查询当天创建的预约数据
        //第二天的开始时间和第二天的18点
        Date startDate = DateUtil.beginOfDay( DateUtil.offset(new Date(), DateField.DAY_OF_MONTH,1));
        Date endDate = DateUtil.offset( startDate, DateField.HOUR_OF_DAY,18);
        List<BuPrescriptionItemAppointmentItem> list = buPrescriptionItemAppointmentItemService.selectTreatmentRemindList(startDate, endDate);
        Map<String, String> map = new HashMap<>();
        map.put("code", "123456");
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                smsService.sendSms(signName,SmsTemplateEnum.VERIFICATION_CODE.getCode(), item.getPhone(), map);
            });
        }
    }
}
