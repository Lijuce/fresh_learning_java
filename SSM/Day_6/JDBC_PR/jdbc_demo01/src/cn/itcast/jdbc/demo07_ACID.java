package cn.itcast.jdbc;
/*
 * 2021年4月14日
 * JDBC Demo7
 * 事务管理（入门）
 * 保证事务的一致性，即要么全部执行，要么全部回滚，不存在只执行一部分SQL语句的情况
 */
import cn.itcast.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 事务管理
 */
public class demo07_ACID {
    public static void main(String[] args) {
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        Connection conn = null;
        try {
            conn = JDBCUtils.Connect();

            // 开启事务
            conn.setAutoCommit(false);

            String sql1 = "update accountTable set balance = balance + ? where id = ?";
            String sql2 = "update accountTable set balance = balance - ? where id = ?";
            pstm1 = conn.prepareStatement(sql1);
            pstm2 = conn.prepareStatement(sql2);
            pstm1.setInt(1, 500);
            pstm1.setInt(2, 1);
            pstm1.executeUpdate();

            pstm2.setInt(1, 500);
            pstm2.setInt(2,2);
            pstm2.executeUpdate();

//            int i = 3/0;  // 手动异常
            // 提交事务
            conn.commit();

        } catch (Exception throwables) {
            // 事务回滚
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(conn, pstm1);
            JDBCUtils.close(null,pstm2);
        }
    }
}
