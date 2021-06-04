package com.utli;

import com.domain.Account;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用于存放查询的多个用户的集合工具类
 */
public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("uid"));
        account.setName(resultSet.getString("name"));
        return account;
    }
}
