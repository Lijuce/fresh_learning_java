/*
 * 2021年4月14日
 * JDBC Demo6
 * 登录练习案例
 * 附带：preparedStatement方法的使用练习，防止SQL注入
 */
package cn.itcast.jdbc;

import cn.itcast.util.JDBCUtils;

import java.sql.*;
import java.util.Scanner;

public class demo6_registerTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入用户名：");
        String username = sc.nextLine();
        System.out.println("输入密码：");
        String password = sc.nextLine();
        boolean flag = new demo6_registerTest().Login(username, password);
        if (flag)
            System.out.println("登录成功");
        else
            System.out.println("登录失败");
    }
    public boolean Login(String username, String password){
        if (username == null && password == null)
            return false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        try {
            conn = JDBCUtils.Connect();
//            stmt = conn.createStatement();
//            String sql = "select * from UserTable where username = \""+ username + "\" and password = \""+password + "\"";
            String sql = "select * from UserTable where username = ? and password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 给？赋值
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            res = pstmt.executeQuery();  // 无需传入SQL参数
//            res = stmt.executeQuery(sql);
            if (res.next())
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(res, conn, stmt);
        }
        return false;
    }
}
