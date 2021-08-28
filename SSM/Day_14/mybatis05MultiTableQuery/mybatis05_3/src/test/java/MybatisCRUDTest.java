import com.dao.IRoleDao;
import com.domain.Role;
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
import java.util.List;

public class MybatisCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IRoleDao userDao;

    @Test
    public void findAll(){
        // 一对一查询测试
        List<Role> all = userDao.findAll();
        for (Role a : all){
            System.out.println(a);
            System.out.println(a.getUsers());
        }
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
        userDao = session.getMapper(IRoleDao.class);
    }

    @After  // 表示在测试方法执行之后进行收尾
    public void destory() throws IOException {
        session.commit();  // 事务提交
        // 7. 释放资源
        session.close();
        in.close();
    }
}
