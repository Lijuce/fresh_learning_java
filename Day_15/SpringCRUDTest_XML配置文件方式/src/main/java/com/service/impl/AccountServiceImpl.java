package com.service.impl;

import com.dao.IAccountDao;
import com.domain.Account;
import com.service.IAccountService;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements IAccountService {
    private IAccountDao accountDao;

    @Override
    public void addAccountService(Account account) throws SQLException {
        accountDao.addAccount(account);
    }

    @Override
    public void deleteAccountService(Integer accountId) throws SQLException {
        accountDao.deleteAccount(accountId);
    }

    @Override
    public void updateAccountService(Account account) throws SQLException {
        accountDao.update(account);
    }

    @Override
    public Account findAccountById(Integer accountId) throws SQLException {
        return accountDao.findById(accountId);
    }

    @Override
    public List<Account> findAll() throws SQLException {
        return accountDao.findAll();
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
