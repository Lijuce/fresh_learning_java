package com.dao;

import com.domain.Account;
import com.utli.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AccountDaoImpl implements IAccountDao{
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account findById(Integer id) {
        List<Account> query = jdbcTemplate.query("select * from user where uid=?", new AccountRowMapper(), id);
        return query.isEmpty()? null: query.get(0);
    }

    @Override
    public Account findByName(String name) {
        List<Account> query = jdbcTemplate.query("select * from user where name=?", new AccountRowMapper(), name);
        if (query.isEmpty())
            return null;
        if (query.size() > 1)
            throw new RuntimeException("结果集不唯一");
        return query.get(0);
    }

    @Override
    public void updateAccount(Account account) {
        jdbcTemplate.update("update user set money = ? where id = ?", account.getName(), account.getId());
    }
}
