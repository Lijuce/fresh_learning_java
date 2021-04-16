/*
 * 2021年4月14日
 * JDBC Demo5
 * 主要演示自定义进行封装的JDBC工具类
 * 简化Demo4的部分代码
 */

package cn.itcast.jdbc;

import cn.itcast.domain.stu;
import cn.itcast.util.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class demo5 {
    public static void main(String[] args) {
        demo5 d = new demo5();
        List<stu> list = d.finaAll();
        System.out.println(list);
        System.out.println(list.size());
    }

    /**
     * 封装结果集
     * @return 包装了stu对象的数组列表
     */
    public List<stu> finaAll(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        List<stu> list = null;
        try {
            conn = JDBCUtils.Connect();
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "mysql");
            stmt = conn.createStatement();
            String sql = "select * from student";
            res = stmt.executeQuery(sql);
            stu student = null;
            list = new ArrayList<>();
            while (res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                student = new stu();
                student.setId(id);
                student.setName(name);

                list.add(student);

            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(res, conn, stmt);
        }

        return list;
    }

}
