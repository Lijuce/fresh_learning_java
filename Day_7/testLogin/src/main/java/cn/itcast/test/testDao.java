package cn.itcast.test;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class testDao {
    @Test
    public void testLogin(){
        User useLogin = new User();
        useLogin.setUsername("Lijuce");
        useLogin.setPassword("123");

        UserDao dao = new UserDao();
        User login = dao.login(useLogin);
        System.out.println(login);

    }

}
