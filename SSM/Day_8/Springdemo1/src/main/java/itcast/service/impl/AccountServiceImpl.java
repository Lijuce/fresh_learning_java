package itcast.service.impl;

import itcast.dao.Dao.AccountDao;
import itcast.dao.Dao.Impl.AccountDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("accountService")
//@Service("accountService")
public class AccountServiceImpl implements AccountService{

//    private AccountDaoImpl accountDao= new AccountDaoImpl();
    @Autowired
    private AccountDao accountDao;
    /**
     * 默认构造函数
     */
    public AccountServiceImpl(){
        System.out.println("对象创建了");
    }

    public void saveAccount() {
        accountDao.saveAccount();
        System.out.println("调用了AccountService中的saveAccount方法");
    }
}
