import com.dao.impl.AccountDaoImpl;
import com.domain.Account;
import com.service.IAccountService;
import com.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class JDBCTemplateTest {

    @Autowired
    private IAccountService as;
    @Test
    public void test(){
        Account account = as.findAccount(1);
        System.out.println(account);
    }
}
