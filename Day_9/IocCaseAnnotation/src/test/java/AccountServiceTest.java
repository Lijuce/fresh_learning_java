import config.SpringConfiguration;
import com.itcast.domain.Account;
import com.itcast.service.IAccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class AccountServiceTest {
    @Test
    public void test(){
//        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        IAccountService as = (IAccountService) ac.getBean("accountService");
//        System.out.println(as);
        List<Account> allAccount = as.findAllAccount();
//        System.out.println(allAccount);
        for (Account a: allAccount){
            System.out.println(a);
        }
    }

    @Test
    public void test1(){
        int[] a = {2,1,3,4};
        Arrays.sort(a);

        for (int i: a)
            System.out.println(i);
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "aaa");
        System.out.println(map);

    }
}
