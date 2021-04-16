package cn.itcast.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/*
 * 2021年4月15日
 * JDBC Demo8
 * Druid入门使用
 */
public class demo08_DruidTest {
    public static void main(String[] args) throws Exception {
        // 1. 导入Druid相应jar包
        // 2. 定义和配置properties文件
        Properties pro = new Properties();
        InputStream proFile = demo08_DruidTest.class.getClassLoader().getResourceAsStream("druid.properties");
        // 获取文件绝对路径后进行加载
        pro.load(proFile);
        // 3. 创建数据库连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pro);
        // 4. 获取连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);


    }
}
