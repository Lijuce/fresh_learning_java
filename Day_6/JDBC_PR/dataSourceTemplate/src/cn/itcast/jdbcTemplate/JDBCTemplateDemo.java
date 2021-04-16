package cn.itcast.jdbcTemplate;
/**
 * 2021年4月15日
 * JDBCTemplate的快速入门
 * 进行更新数据操作
 */

import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCTemplateDemo {
    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDatasource());
//        String sql = "update accountTable set balance = 1000 where id = ?";
//        int count = jdbcTemplate.update(sql, 1);
//        System.out.println(count);
        System.out.println(jdbcTemplate);

    }
}
