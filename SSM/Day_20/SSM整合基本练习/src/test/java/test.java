import com.dao.IAccountDao;
import com.domain.Account;
import com.service.IAccountService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class test {
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        List<Account> all = accountService.findAll();
        for (Account account: all)
            System.out.println(account);
    }

    @Test
    public void testMybatis() throws IOException {
        // 单独测试Mybatis
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        IAccountDao mapper = sqlSession.getMapper(IAccountDao.class);
        List<Account> all = mapper.findAll();
        for (Account account: all)
            System.out.println(account);
        sqlSession.close();
        in.close();
    }
}
