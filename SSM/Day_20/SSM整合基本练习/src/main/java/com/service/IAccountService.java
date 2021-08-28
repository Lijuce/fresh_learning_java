package com.service;

import com.domain.Account;

import java.util.List;

public interface IAccountService {
    Account findById(Integer id);

    List<Account> findAll();
}
