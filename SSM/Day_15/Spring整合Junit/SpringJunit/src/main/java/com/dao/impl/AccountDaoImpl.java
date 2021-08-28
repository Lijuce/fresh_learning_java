package com.dao.impl;

import com.dao.IAccountDao;
import com.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class AccountDaoImpl implements IAccountDao {
    private QueryRunner queryRunner;

    public void setQueryRunner(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    @Override
    public void addAccount(Account account) throws SQLException {
        queryRunner.update("insert into user(uid, name ) values (?, ?)", account.getId(), account.getName());
    }

    @Override
    public void deleteAccount(Integer accountId) throws SQLException {
        queryRunner.update("delete from user where uid = ?", accountId);
    }

    @Override
    public void update(Account account) throws SQLException {
        queryRunner.update("update user set name = ? where uid = ?", account.getName(), account.getId());
    }

    @Override
    public List<Account> findAll() throws SQLException {
        return queryRunner.query("select u.uid id, u.name from user u", new BeanListHandler<Account>(Account.class));
    }

    @Override
    public Account findById(Integer accountId) throws SQLException {
        return queryRunner.query("select u.uid id, u.name from user u where uid=?", new BeanHandler<>(Account.class), accountId);
    }
}
