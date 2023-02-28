package com.maizhiyu.yzt.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import com.maizhiyu.yzt.service.ISmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import wiremock.com.google.common.base.Splitter;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@Slf4j
public class AppointmentRemindTask {

    @Value("${sms.signName}")
    private String signName;

    @Resource
    private BuPrescriptionItemTaskService buPrescriptionItemTaskService;

    @Resource
    private ISmsService smsService;

    //当天18:00提醒及时最近一次预约时间及查看方式
    @Scheduled(cron = "0 0 18 * * ?")
    private void RemindLatestAppointmentTask() {
        //查询当天创建的预约数据
        Date startDate = DateUtil.beginOfDay(new Date());
        List<BuPrescriptionItemTask> list = buPrescriptionItemTaskService.selectRemindLatestAppointmentList(startDate, new Date());
        if(CollectionUtils.isEmpty(list))
            return;
        list.forEach(item -> {
            if(StringUtils.isBlank(item.getPhone()))
                return;

            Map<String, String> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("hospital", item.getCustomerName());
            map.put("department", item.getDname());
            map.put("project", item.getTsName());
            String day = Splitter.on(' ').splitToList(item.getAppointmentDate().toLocaleString()).stream().collect(Collectors.toList()).get(0);
            map.put("time", day + ' ' + item.getTimeSlot());
            smsService.sendSms(signName,SmsTemplateEnum.REMIND_LATEST_APPOINTMENT.getCode(), item.getPhone(), map);
        });
    }

    //当天18:00提醒及时最近一次预约时间及查看方式
    @Scheduled(cron = "0 0 18 * * ?")
    private void TreatmentRemindTask() {
        //查询当天创建的预约数据
        //第二天的开始时间和第二天的18点
        Date startDate = DateUtil.beginOfDay( DateUtil.offset(new Date(), DateField.DAY_OF_MONTH,1));
        Date endDate = DateUtil.offset( startDate, DateField.HOUR_OF_DAY,18);
        List<BuPrescriptionItemTask> list = buPrescriptionItemTaskService.selectTreatmentRemindList(startDate, endDate);
        if(CollectionUtils.isEmpty(list))
            return;
        list.forEach(item -> {
            if(StringUtils.isBlank(item.getPhone()))
                return;

            Map<String, String> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("hospital", item.getCustomerName());
            map.put("department", item.getDname());
            map.put("project", item.getTsName());
            map.put("time", item.getTimeSlot());
            smsService.sendSms(signName,SmsTemplateEnum.REMIND_TREATMENT.getCode(), item.getPhone(), map);
        });
    }
}
