package com.itcast.service;

import com.itcast.domain.Account;

import java.util.List;

/**
 * yewuceng  jiekoud
 */
public interface IAccountService {
    /**
     * chaxun suoyou
     * @return
     */
    List<Account> findAllAccount();

}
