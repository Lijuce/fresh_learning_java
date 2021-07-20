package com.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName QuartzDemo
 * @Description Quartz框架使用Demo
 * @Author Lijuce_K
 * @Date 2021/7/20 16:05
 * @Version 1.0
 **/
public class QuartzDemo {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "myGroup")
                .usingJobData("name", "lijuce")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", "triggerGroup")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * ? * *"))
                .startNow()
                .build();
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
