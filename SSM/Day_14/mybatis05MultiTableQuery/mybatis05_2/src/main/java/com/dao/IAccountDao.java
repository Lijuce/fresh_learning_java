package com.dao;

import com.domain.Account;
import com.domain.User;

import java.util.List;

public interface IAccountDao {

    /**
     * 查询所有用户，同时获取其名下的所有账户信息
     * @return
     */
    List<User> findAll();
}
