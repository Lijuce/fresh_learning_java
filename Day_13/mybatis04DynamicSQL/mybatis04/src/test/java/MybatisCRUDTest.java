import com.dao.UserDao;
import com.domain.QueryVo;
import com.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private UserDao userDao;

    @Test
    public void  testFindInIds(){
        QueryVo queryVo = new QueryVo();
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(13);
        ids.add(14);
        ids.add(15);
        queryVo.setIds(ids);
        List<User> inIds = userDao.findInIds(queryVo);
        for (User u: inIds)
            System.out.println(u);
    }

    @Test
    public void findByUser(){
        User user = new User();
        user.setName("%吴%");
        List<User> users = userDao.findByUser(user);
        for (User u: users)
            System.out.println(u);
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
