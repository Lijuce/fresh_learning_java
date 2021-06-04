package com.dao;


import com.domain.Account;

public interface IAccountDao {
    Account findById(Integer id);

    Account findByName(String name);

    void updateAccount(Account account);
}
