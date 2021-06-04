package com.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;

/**
 * JDBCTemplate最基本的使用
 */
public class jdbcDemo1 {
    public static void main(String[] args){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        ds.setUser("root");
        ds.setPassword("mysql");

        JdbcTemplate jt = new JdbcTemplate(ds);
        jt.execute("insert into user (uid, name) values (10, 'lijuce')");
    }
}
