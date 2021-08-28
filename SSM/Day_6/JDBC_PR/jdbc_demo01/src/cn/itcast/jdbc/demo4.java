/*
 * 2021年4月14日
 * JDBC Demo4
 * 结合SQL 进行查询操作
 * 搭配自定义类 stu
 * 将结果集进行封装，然后整体输出
 * 注意List<>的使用
 */

package cn.itcast.jdbc;

import cn.itcast.domain.stu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class demo4 {
    public static void main(String[] args) {
        demo4 d = new demo4();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "mysql");
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

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
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

        return list;
    }

}
