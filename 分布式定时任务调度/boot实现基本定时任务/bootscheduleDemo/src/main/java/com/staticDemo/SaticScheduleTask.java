package com.staticDemo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName SaticScheduleTask
 * @Description 静态的基于注解实现定时任务
 * @Author Lijuce_K
 * @Date 2021/7/27 10:17
 * @Version 1.0
 **/
@Component
public class SaticScheduleTask {
    @Scheduled(cron = "0/3 * * * * ?")
    private void configuration() {
        System.out.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
