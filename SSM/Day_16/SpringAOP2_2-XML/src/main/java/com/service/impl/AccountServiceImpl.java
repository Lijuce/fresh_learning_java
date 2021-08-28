package com.service.impl;

import com.service.IAccountService;

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
