package com.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @ClassName MyJob
 * @Description 定时任务具体执行内容
 * @Author Lijuce_K
 * @Date 2021/7/20 16:00
 * @Version 1.0
 **/
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行：" + new Date() + jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
    }
}
