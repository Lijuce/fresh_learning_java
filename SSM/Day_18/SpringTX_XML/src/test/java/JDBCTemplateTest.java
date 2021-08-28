import com.dao.impl.AccountDaoImpl;
import com.domain.Account;
import com.service.IAccountService;
import com.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JDBCTemplateTest {

    @Test
    public void test(){
        ApplicationContext cs = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService = cs.getBean("accountService", IAccountService.class);  // 此处必须用接口类型强制转换，不能用实现类
        Account account = accountService.findAccount(1);
        System.out.println(account);
//        jdbcTemplate.findById(1);
//        jdbcTemplate.execute("insert into user (uid, name) values (11, 'lijuce')");
//        System.out.println(jdbcTemplate);
    }
}
