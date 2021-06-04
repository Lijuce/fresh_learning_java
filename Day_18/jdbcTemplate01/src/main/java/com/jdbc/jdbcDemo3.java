package com.jdbc;

import com.dao.AccountDaoImpl2;
import com.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 用Spring自带的JdbcDaoSupport，进一步优化JDBCTemplate的业务代码
 */
public class jdbcDemo3 {
    public static void main(String[] args) {
        ApplicationContext cs = new ClassPathXmlApplicationContext("Bean.xml");
        AccountDaoImpl2 accountDao2 = cs.getBean("accountDao2", AccountDaoImpl2.class);
        Account account = accountDao2.findById(1);
        System.out.println(account);
    }
}
