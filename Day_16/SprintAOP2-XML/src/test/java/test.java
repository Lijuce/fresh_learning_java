import com.BeanFactory;
import com.service.IAccountService;
import com.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class test {
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService =(IAccountService) ac.getBean("accountService");
        accountService.findAccount(1);
        accountService.addAcount();
        accountService.updateAccount();
    }
}
