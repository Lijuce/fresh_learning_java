package cn.itcast.dataSource;
/**
 * 2021年4月15日
 * Druid连接池初步尝试
 * 仅做连接尝试，并输出连接对象，没有做任何数据库操作
 */

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidDemo {
    public static void main(String[] args) throws Exception {
        // 定义、配置和加载properties文件
        Properties properties = new Properties();
        InputStream resourceAsStream = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        // 创建连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        // 获取连接
        Connection conn = dataSource.getConnection();
        System.out.println(conn);

    }
}
