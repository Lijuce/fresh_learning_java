package com.itcast.dao.Impl;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.itcast.dao.IAccountDao;
import com.itcast.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * dao shixian lei
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner runner;
//    private DruidDataSourceFactory

//    public void setRunner(QueryRunner runner){
//        this.runner = runner;
//    }

    @Override
    public List<Account> findAllAccount() {
        try {
            String sql = "select * from account";
            List<Account> queryRes = runner.query(sql, new BeanListHandler<>(Account.class));
            return queryRes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
