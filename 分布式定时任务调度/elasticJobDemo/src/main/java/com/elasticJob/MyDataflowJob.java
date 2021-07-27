package com.elasticJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MyDataflowJob
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/7/27 16:48
 * @Version 1.0
 **/
public class MyDataflowJob implements DataflowJob<String> {

    private static final ThreadLocal<Integer> LOOP_COUNTER = new ThreadLocal<>();
    //每次获取流处理循环次数
    private static final int LOOP_TIMES = 10;
    //计数器
    private static final AtomicInteger COUNTER = new AtomicInteger(1);


    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        Integer curInt = LOOP_COUNTER.get();
        if (curInt == null) {
            curInt = 1;
        } else {
            curInt += 1;
        }

        LOOP_COUNTER.set(curInt);
        System.out.println(Thread.currentThread() + "------------current--------" + curInt);
        if (curInt > LOOP_TIMES) {
            System.out.println("\n\n\n");
            return null;
        } else {
            int shardingItem = shardingContext.getShardingItem();
            List<String> datas = Arrays.asList(getData(shardingItem), getData(shardingItem), getData(shardingItem));
            return datas;
        }
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        System.out.println(Thread.currentThread() + "-------" + data);
    }

    private String getData(int shardingItem) {
        return shardingItem + "-" + COUNTER.getAndIncrement();
    }
}
