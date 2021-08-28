/*
* 2021年4月14日
* JDBC Demo1
* 快速上手使用，最基本用法
*/

package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class demo1 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1. 导入jar包于libs
        //2. 注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3. 获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "mysql");
        //4. 定义SQL语句
        String sql = "update student set name='老王' where id = 1";
        //5. 获取执行SQL的statement对象
        Statement stmt = conn.createStatement();
        //6. 执行SQL
        int count = stmt.executeUpdate(sql);
        //7. 处理。。。
        System.out.println(count);
        //8. 释放数据库
        stmt.close();
        conn.close();
    }


}
