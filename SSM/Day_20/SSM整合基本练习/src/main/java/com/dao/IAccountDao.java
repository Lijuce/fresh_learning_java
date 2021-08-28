package com.dao;

import com.domain.Account;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户dao接口
 */

@Repository  // 加上注解@Repository，将其交于IOC容器管理
public interface IAccountDao {

    Account findById(Integer id);

    // 注解方式编写：查询所有账户（SQL）
    @Select("select * from user")
    List<Account> findAll();
}
