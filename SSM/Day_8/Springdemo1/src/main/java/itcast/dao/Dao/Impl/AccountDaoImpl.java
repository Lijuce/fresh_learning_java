package itcast.dao.Dao.Impl;

import itcast.dao.Dao.AccountDao;
import org.springframework.stereotype.Component;

@Component
public class AccountDaoImpl implements AccountDao {
    @Override
    public void saveAccount() {
        System.out.println("账户保存！");
    }
}
