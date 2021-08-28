package com.service.impl;

import com.dao.IAccountDao;
import com.dao.impl.AccountDaoImpl;
import com.domain.Account;
import com.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountDaoImpl accountDao;

    @Override
    public Account findAccount(Integer id) {
        return accountDao.findAccountById(id);
    }
}
