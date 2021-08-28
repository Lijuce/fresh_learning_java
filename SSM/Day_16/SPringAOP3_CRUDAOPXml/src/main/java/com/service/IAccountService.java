package com.service;

import com.domain.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountService {

    void addAccountService(Account account) throws SQLException;

    void deleteAccountService(Integer accountId) throws SQLException;

    void updateAccountService(Account account) throws SQLException;

    Account findAccountById(Integer accountId) throws SQLException;

    List<Account> findAll() throws SQLException;
}
