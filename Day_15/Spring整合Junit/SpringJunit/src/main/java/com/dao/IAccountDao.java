package com.dao;

import com.domain.Account;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户持久层接口
 */
public interface IAccountDao {

    /**
     * 增添新用户
     * @param account
     */
    void addAccount(Account account) throws SQLException;

    /**
     * 根据ID删除用户
     * @param accountId
     */
    void deleteAccount(Integer accountId) throws SQLException;

    /**
     * 更新用户
     * @param account
     */
    void update(Account account) throws SQLException;

    /**
     * 根据ID查询用户
     * @param accountId
     * @return
     * @throws SQLException
     */
    Account findById(Integer accountId) throws SQLException;

    /**
     * 查询所有用户
     * @return
     */
    List<Account> findAll() throws SQLException;
}
