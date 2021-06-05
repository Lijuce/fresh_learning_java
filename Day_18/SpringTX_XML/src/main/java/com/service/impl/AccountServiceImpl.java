package com.service.impl;

import com.dao.IAccountDao;
import com.dao.impl.AccountDaoImpl;
import com.domain.Account;
import com.service.IAccountService;

public class AccountServiceImpl implements IAccountService {
    private AccountDaoImpl accountDao;

    public void setAccountDao(AccountDaoImpl accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account findAccount(Integer id) {
        return accountDao.findAccountById(id);
    }
}
