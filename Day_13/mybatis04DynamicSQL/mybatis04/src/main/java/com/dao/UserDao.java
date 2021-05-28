package com.dao;

import com.domain.QueryVo;
import com.domain.User;

import java.util.List;

/**
 * 持久层接口
 */
public interface UserDao {
    /**
     * 根据用户类进行查询
     * @param user
     * @return
     */
    List<User> findByUser(User user);

    /**
     * 根据ID集合查询用户
     * @param vo
     * @return
     */
    List<User> findInIds(QueryVo vo);
}
