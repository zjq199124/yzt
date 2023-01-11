package com.maizhiyu.yzt.task;

import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
@Slf4j
public class AppointmentRemindTask {

    @Resource
    private IBuPrescriptionItemAppointmentService buPrescriptionItemAppointmentService;

    //当天18:00提醒及时最近一次预约时间及查看方式
    @Scheduled(cron = "0 0 18 * * ?")
    private void Task() {
        //查询当天创建的预约数据
    }
}
