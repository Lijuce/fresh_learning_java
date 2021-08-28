import com.dao.UserDao;
import com.domain.QueryVo;
import com.domain.User;
import com.domain.UserOthers;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private UserDao userDao;


    @Test  // 为方便进行测试，此处借助Junit测试类进行单元测试
    public void findById(){
        // 测试数据库查询操作
        User user = userDao.findById(2);
        System.out.println(user);
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setName("abcd");
        user.setPassword("123456");
        userDao.addUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void updateUser(){
        User user = userDao.findById(22);
        user.setPassword("a123456");
        int res = userDao.updateUser(user);
        if (res > 0)
            System.out.println("成功更新用户信息");
    }

    @Test
    public void deleteUser(){
        int res = userDao.deleteUser(20);
        if (res > 0)
            System.out.println("成功删除用户");
    }

    @Test
    public void findUserByName(){
        // 模糊查询测试
        List<User> users = userDao.findByName("%吴%");
        for (User user: users)
            System.out.println(user);
    }

    @Test
    public void findUserByVo(){
        // pojo作为参数进行模糊查询 测试
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setName("%吴%");
        queryVo.setUser(user);
        List<User> byVo = userDao.findByVo(queryVo);
        for (User u : byVo)
            System.out.println(u);
    }

    @Test
    public void findUserOthersAll(){
        List<UserOthers> all1 = userDao.findAll1();
        for (UserOthers e: all1)
            System.out.println(e);
    }

    @Before  // 表：在测试方法执行之前初始化
    public void init() throws IOException {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建 SqlSessionFactory 的构建者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3.使用构建者创建工厂对象 SqlSessionFactory
        factory = builder.build(in);
        //4.使用 SqlSessionFactory 生产 SqlSession 对象
        session = factory.openSession();
        //5.使用 SqlSession 创建 dao 接口的代理对象
        userDao = session.getMapper(UserDao.class);
    }

    @After  // 表示在测试方法执行之后进行收尾
    public void destory() throws IOException {
        session.commit();  // 事务提交
        // 7. 释放资源
        session.close();
        in.close();
    }
}
