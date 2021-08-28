package com.dao.impl;

import com.dao.IAccountDao;
import com.domain.Account;
import com.util.AccountRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class AccountDaoImpl extends JdbcDaoSupport implements IAccountDao {
    @Override
    public Account findAccountById(Integer id) {
//        List<Account> accounts = super.getJdbcTemplate().query("select * from user where uid=?", new AccountRowMapper(), id);
        List<Account> accounts = super.getJdbcTemplate().query("select * from user where uid=?", new BeanPropertyRowMapper<Account>(Account.class), id);
        return accounts.isEmpty()? null: accounts.get(0);
    }

//    @Override
//    public Account findAccountByName(String name) {
//        List<Account> accounts = getJdbcTemplate().query("select * from user where name=?", new AccountRowMapper(), name);
//        if (accounts.isEmpty())
//            return null;
//        if (accounts.size() > 1){
//            throw new RuntimeException("结果不唯一");
//        }
//        return accounts.get(0);
//    }
//
//    @Override
//    public void updateAccount(Account account) {
//        getJdbcTemplate().update("update user set name = ? where uid = ?", account.getName(), account.getId());
//    }
}
