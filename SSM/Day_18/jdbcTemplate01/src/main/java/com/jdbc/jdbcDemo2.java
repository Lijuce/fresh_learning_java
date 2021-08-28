package com.jdbc;

import com.utli.AccountRowMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * XML配置方式使用JDBCTemplate
 */
public class jdbcDemo2 {
    public static void main(String[] args) {
        ApplicationContext cs = new ClassPathXmlApplicationContext("Bean.xml");
        JdbcTemplate jdbcTemplate = cs.getBean("jdbcTemplate", JdbcTemplate.class);
//        jdbcTemplate.execute("insert into user (uid, name) values (11, 'lijuce')");
        jdbcTemplate.query("select * from user where uid = ?", new AccountRowMapper(), 1);
//        System.out.println(jdbcTemplate);

    }
}
