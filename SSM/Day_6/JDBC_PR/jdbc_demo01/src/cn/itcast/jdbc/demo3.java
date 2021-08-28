/*
 * 2021年4月14日
 * JDBC Demo3
 * 结合SQL 进行查询操作
 * executeQuery方法的使用
 * 注意 游标 1、res.next()的使用
 *          2、res本身也要被释放
 */

package cn.itcast.jdbc;

import java.sql.*;

public class demo3 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "mysql");
            stmt = conn.createStatement();
            String sql = "Select * from student";
            res = stmt.executeQuery(sql);

            while (res.next()){
//            res.next();  // 游标指向下一行数据
                int id = res.getInt("id");
                String name = res.getString("name");
                System.out.println(id);
                System.out.println(name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }

    }
}
