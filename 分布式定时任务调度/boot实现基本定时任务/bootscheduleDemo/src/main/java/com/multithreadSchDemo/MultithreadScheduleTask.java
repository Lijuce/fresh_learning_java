package com.multithreadSchDemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName MultithreadScheduleTask
 * @Description boot实现多线程定时任务
 * @Author Lijuce_K
 * @Date 2021/7/27 10:34
 * @Version 1.0
 **/
@Component
@EnableAsync
public class MultithreadScheduleTask {

    @Async
    @Scheduled(fixedDelay = 1000)
    public void first() throws InterruptedException {
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
        Thread.sleep(1000 * 10);
    }

    @Async
    @Scheduled(fixedDelay = 2000)
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }
}
