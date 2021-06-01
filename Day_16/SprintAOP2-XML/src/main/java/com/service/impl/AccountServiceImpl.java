package com.service.impl;

import com.dao.IAccountDao;
import com.domain.Account;
import com.service.IAccountService;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements IAccountService {
    @Override
    public void addAcount() {
        System.out.println("添加账户");
    }

    @Override
    public void updateAccount() {
        System.out.println("更新账户。。。。");
    }

    @Override
    public void findAccount(int i) {
        System.out.println("查询账户");
    }
}
