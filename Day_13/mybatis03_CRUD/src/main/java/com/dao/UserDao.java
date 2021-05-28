package com.dao;

import com.domain.QueryVo;
import com.domain.User;
import com.domain.UserOthers;
import org.apache.ibatis.annotations.Select;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * 持久层接口
 */
public interface UserDao {

    List<UserOthers> findAll1();

    List<User> findAll();

    /**
     * 根据ID查询用户
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 增添新用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据用户ID删除用户
     * @param id
     * @return
     */
    int deleteUser(Integer id);

    /**
     * 根据用户名查找用户-模糊查询
     * @param name
     * @return
     */
    List<User> findByName(String name);

    /**
     * 根据pojo类查询用户
     * @param vo
     * @return
     */
    List<User> findByVo(QueryVo vo);
}
