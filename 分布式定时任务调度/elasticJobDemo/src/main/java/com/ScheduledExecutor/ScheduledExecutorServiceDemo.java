package com.ScheduledExecutor;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @ClassName ScheduledExecutorServiceDemo
 * @Description 线程池支持下的定时任务调度
 * @Author Lijuce_K
 * @Date 2021/7/20 9:10
 * @Version 1.0
 **/
public class ScheduledExecutorServiceDemo implements Runnable{
    @Override
    public void run() {
        System.out.println("执行：" + new Date());
    }

    public static void main(String[] args) throws ParseException {
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
        scheduledExecutorService.scheduleAtFixedRate(new ScheduledExecutorServiceDemo(), 1000, 2000, TimeUnit.MILLISECONDS);
    }


}
