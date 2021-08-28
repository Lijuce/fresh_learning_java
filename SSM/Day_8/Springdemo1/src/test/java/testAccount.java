import itcast.dao.Dao.AccountDao;
import itcast.service.impl.AccountService;
import itcast.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testAccount {
    @Test
    void test(){
//        AccountServiceImpl accountService = new AccountServiceImpl();
//        accountService.saveAccount();
//            ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
//        AccountService accountService = (AccountService) classPathXmlApplicationContext.getBean("accountService");  // 第一种方式:显示强制类型转换
//        AccountDao acountDao = classPathXmlApplicationContext.getBean("accountDao", AccountDao.class);// 另一种方式:隐式强转
//        System.out.println(accountService);
//        System.out.println(acountDao);

        ClassPathXmlApplicationContext cpa = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) cpa.getBean("accountService");
//        System.out.println(accountService);
        accountService.saveAccount();


    }
}
