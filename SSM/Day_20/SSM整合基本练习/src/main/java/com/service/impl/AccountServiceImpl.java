package com.service.impl;

import com.dao.IAccountDao;
import com.domain.Account;
import com.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    public Account findById(Integer id) {
        System.out.println("findById方法调用。。。");
        return null;
    }

    public List<Account> findAll() {
        System.out.println("findAll方法调用。。。");

        return accountDao.findAll();
    }
}
