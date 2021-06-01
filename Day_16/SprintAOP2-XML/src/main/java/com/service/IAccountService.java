package com.service;

import com.domain.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountService {

    void addAcount();

    void updateAccount();

    void findAccount(int i);
}
