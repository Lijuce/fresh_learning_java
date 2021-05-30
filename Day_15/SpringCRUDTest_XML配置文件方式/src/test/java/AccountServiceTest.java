import com.domain.Account;
import com.service.IAccountService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceTest {
    @Test
    public void testFindById() throws SQLException {
        ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = cs.getBean("accountService", IAccountService.class);
        System.out.println(as);
        Account accountById = as.findAccountById(1);
        System.out.println(accountById);
    }

    @Test
    public void testAddAccount(){
        ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("bean.xml");
        Account account = new Account();
        account.setId(4);
        account.setName("Lijuce");
        IAccountService as = cs.getBean("accountService", IAccountService.class);
        try {
            as.addAccountService(account);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void  testUpdateAccount() throws SQLException {
        ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = cs.getBean("accountService", IAccountService.class);
        Account accountById = as.findAccountById(4);
        System.out.println(accountById);
        accountById.setName("老吴");
        System.out.println(accountById);
        as.updateAccountService(accountById);
        System.out.println("update 》。。。");
    }

    @Test
    public void testDeleteAccount() throws SQLException {
        ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("Bean.xml");
        IAccountService accountService = cs.getBean("accountService", IAccountService.class);
        accountService.deleteAccountService(4);
    }

    @Test
    public void testFindAll() throws SQLException {
        ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("Bean.xml");
        IAccountService accountService = cs.getBean("accountService", IAccountService.class);
        List<Account> all = accountService.findAll();
        for (Account a: all)
            System.out.println(a);
    }
}
