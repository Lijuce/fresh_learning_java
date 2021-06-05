package com.dao.impl;

import com.dao.IAccountDao;
import com.domain.Account;
import com.util.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account findAccountById(Integer id) {

        List<Account> accounts = jdbcTemplate.query("select * from user where uid=?", new BeanPropertyRowMapper<Account>(Account.class), id);
        return accounts.isEmpty()? null: accounts.get(0);
    }


}
