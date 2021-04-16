/*
 * 2021年4月14日
 * JDBC Demo2
 * 结合SQL 插入/删除/修改操作
 * 相比Demo1，此次在捕获异常和处理方面更加完善
 * 避免数据库连接和创建对象方面出错而导致没有释放资源
 */

package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class demo2 {
    public static void main(String[] args) {
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "mysql");
            String sql = "Insert into Student values(4, \"李六\")";
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            System.out.println(count);
            if(count > 0)
                System.out.println("添加成功");
            else
                System.out.println("添加失败");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stmt.close();  // 放在final进行处理
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }
}
