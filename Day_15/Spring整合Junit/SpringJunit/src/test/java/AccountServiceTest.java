import com.domain.Account;
import com.service.IAccountService;
import com.service.impl.AccountServiceImpl;
import com.sun.xml.internal.ws.developer.UsesJAXBContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {

    @Autowired
    private IAccountService as;

    @Test
    public void testFindById() throws SQLException {
        Account accountById = as.findAccountById(1);
        System.out.println(accountById);
    }

    @Test
    public void testAddAccount(){
        Account account = new Account();
        account.setId(4);
        account.setName("Lijuce");
        try {
            as.addAccountService(account);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void  testUpdateAccount() throws SQLException {
        Account accountById = as.findAccountById(4);
        System.out.println(accountById);
        accountById.setName("老吴");
        System.out.println(accountById);
        as.updateAccountService(accountById);
        System.out.println("update 》。。。");
    }

    @Test
    public void testDeleteAccount() throws SQLException {
        as.deleteAccountService(4);
    }

    @Test
    public void testFindAll() throws SQLException {
        List<Account> all = as.findAll();
        for (Account a: all)
            System.out.println(a);
    }
}
