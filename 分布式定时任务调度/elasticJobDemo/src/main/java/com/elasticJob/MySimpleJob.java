package com.elasticJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName MySimpleJob
 * @Description 任务调度具体执行作业内容
 * @Author Lijuce_K
 * @Date 2021/7/20 16:49
 * @Version 1.0
 **/
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext context) {
        System.out.println(String.format("分片项 ShardingItem: %s | 运行时间: %s | 线程ID: %s | 分片参数: %s",
                context.getShardingItem(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                Thread.currentThread().getId(),
                context.getShardingParameter()));
        String target = context.getShardingParameter();
        int shardingItem = context.getShardingItem();
        switch (shardingItem) {
            case 0:
                System.out.println("物流发放至：" + target);
                break;
            case 1:
                System.out.println("物流发放至：" + target);
                break;
            case 2:
                System.out.println("物流发放至：" + target);
                break;
            case 3:
                System.out.println("物流发放至：" + target);
                break;
        }
    }
}
