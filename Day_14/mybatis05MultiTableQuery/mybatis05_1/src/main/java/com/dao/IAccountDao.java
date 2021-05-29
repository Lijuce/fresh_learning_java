package com.dao;

import com.domain.Account;

import java.util.List;

public interface IAccountDao {

    /**
     * 查询所有账户，同时获取账户所属用户名及其信息
     * @return
     */
    List<Account> findAll();
}
