package cn.itcast.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {
    static Properties properties = null;
    static DataSource ds = null;
    // 静态代码块，配置文件读取
    static {
        try {
            properties = new Properties();
//            InputStream resourceAsStream = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            ClassLoader classLoader = JDBCUtil.class.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream("druid.properties");
            System.out.println(resourceAsStream);

            properties.load(resourceAsStream);
            // 获取连接池
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接方法
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static DataSource getDatasource(){
        return ds;
    }

    /**
     * 释放连接资源的方法
     * @param stmt
     * @param conn
     */
    public static void close(Statement stmt, Connection conn){
        close(null, stmt, conn);
    }

    public static void close(ResultSet res, Statement stmt, Connection conn){
        if (res != null){
            try {
                res.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
