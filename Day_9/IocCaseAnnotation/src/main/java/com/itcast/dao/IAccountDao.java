package com.itcast.dao;


import com.itcast.domain.Account;

import java.util.List;

public interface IAccountDao {
    /**
     * chaxun suoyou
     * @return
     */
    List<Account> findAllAccount();

}
