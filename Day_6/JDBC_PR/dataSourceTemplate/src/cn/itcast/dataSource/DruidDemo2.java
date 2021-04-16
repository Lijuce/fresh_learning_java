package cn.itcast.dataSource;
/**
 * 2021年4月15日
 * 测试自定义封装好的工具类JDBCUtil
 * Druid连接池与MySQL交互
 * 进行插入数据操作
 */
import cn.itcast.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DruidDemo2 {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into accountTable values (?, ?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, 3);
            ps.setString(2, "aaa");
            ps.setInt(3, 50);
            int count = ps.executeUpdate();
            System.out.println(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(ps, conn);
        }

    }
}
