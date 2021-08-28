/*
 * 2021年4月14日
 * JDBCUtils
 * 自定义进行封装的JDBC工具类
 * 用于简化Demo4的部分代码
 */
package cn.itcast.util;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * 对JDBC进行封装，简化代码，作为工具类
 */
public class JDBCUtils {
    private static String url;  // 注意只有静态变量才能被静态块访问
    private static String user;
    private static String password;
    private static String driver;

    static {
        try {
            // 读取资源配置文件，获取值
            Properties pro = new Properties();
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL propertiesFile = classLoader.getResource("jdbc.properties");
            String path = propertiesFile.getPath();

            pro.load(new FileReader(path));
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");

            // 注册驱动
            Class.forName(driver);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection Connect() throws SQLException{  // 此处选择抛出异常
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(Connection conn, Statement stmt){
        if (conn != null){
            try {
                conn.close();
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

    }

    public static void close(ResultSet res, Connection conn, Statement stmt){
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
