package com.itcast.service.impl;

import com.itcast.dao.IAccountDao;
import com.itcast.domain.Account;
import com.itcast.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * shixian lei
 */
@Service("accountService")
public class AccountServiceImply implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

//    public void setAccountDao(IAccountDao accountDao){
//        this.accountDao = accountDao;
//    }

    @Override
    public List<Account> findAllAccount() {
        return  accountDao.findAllAccount();
    }

}
