package com.elasticJob;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @ClassName SimpleJobTest
 * @Description elastic-job基本使用Demo，搭配zookeeper注册中心
 * @Author Lijuce_K
 * @Date 2021/7/20 16:52
 * @Version 1.0
 **/
public class SimpleJobTest {
    public static void main(String[] args) {
        // ZK注册中心
        ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("127.0.0.1:2181", "ejob-standalone"));
        regCenter.init();

        // 数据源配置
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");
        JobEventConfiguration jobEventConfig = new JobEventRdbConfiguration(dataSource);

        // 定义作业核心配置(simpleJob类型)
        JobCoreConfiguration coreConfig = JobCoreConfiguration
                .newBuilder("MySimpleJob", "0/2 * * ? * *", 4)
                .shardingItemParameters("0=北京,1=上海,2=广州,3=深圳")
                .failover(true)
                .build();
        // 定义作业核心配置(dataFlow类型)
        JobCoreConfiguration myDataflowJobConfig = JobCoreConfiguration
                .newBuilder("MyDataflowJob", "0/2 * * ? * *", 4)
                .shardingItemParameters("0=RDP, 1=CORE, 2=SIMS, 3=ECIF")
                .failover(true)
                .build();

        // 定义Simple类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, MySimpleJob.class.getCanonicalName());
        // 定义dataFlow类型配置
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(myDataflowJobConfig, MyDataflowJob.class.getCanonicalName(), true);

        // 作业分片策略
        String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        // 定义Lite作业根配置
        LiteJobConfiguration jobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
//        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).build();

        // 构建Job
        new JobScheduler(regCenter, jobRootConfig, jobEventConfig).init();


    }
}
