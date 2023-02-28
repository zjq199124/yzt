package com.maizhiyu.yzt.task;

import cn.hutool.core.date.DateUtil;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class CheckOverdueTask {

    @Resource
    private BuPrescriptionItemTaskService buPrescriptionItemTaskService;

    //当天20:00查看逾期情况
    @Scheduled(cron = "0 0 20 * * ?")
    private void checkOverdueTask() {
        //查询当天创建的预约数据
        Date startDate = DateUtil.beginOfDay(new Date());
        List<BuPrescriptionItemTask> list = buPrescriptionItemTaskService.checkOverdueTask(startDate, new Date());
        if(CollectionUtils.isEmpty(list))
            return;

        list.forEach(item -> {
            item.setAppointmentStatus(2);
        });
        buPrescriptionItemTaskService.saveOrUpdateBatch(list);
    }
}
