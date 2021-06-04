package com.dao;

import com.domain.Account;
import com.utli.AccountRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class AccountDaoImpl2 extends JdbcDaoSupport implements IAccountDao {
    @Override
    public Account findById(Integer uid) {
        // getJdbcTemplate方法是继承自内置类JdbcDaoSupport
        // 可直接获取jdbcTemplate，而不用每个DAO都重复创建
        List<Account> query = getJdbcTemplate().query("select * from user where uid=?", new AccountRowMapper(), uid);
        return query.isEmpty()? null: query.get(0);
    }

    @Override
    public Account findByName(String name) {
        return null;
    }

    @Override
    public void updateAccount(Account account) {

    }
}
