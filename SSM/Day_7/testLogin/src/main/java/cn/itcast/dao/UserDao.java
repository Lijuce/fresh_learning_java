package cn.itcast.dao;

import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;

/**
 * 实现User的数据库表操作
 */
public class UserDao {
    // 声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDatasource());

    /**
     *
     * @param loginUser
     * @return
     */
    public User login(User loginUser){
        try {
            // 编写sql
            String sql = "select * from UserTable where username = ? and password = ?";
            // 调用query方法
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), loginUser.getUsername(), loginUser.getPassword());//

            return user;
        }catch (DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

}
