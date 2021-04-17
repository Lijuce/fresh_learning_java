package itcast.factory;

import itcast.service.impl.AccountService;
import itcast.service.impl.AccountServiceImpl;

public class instanceFactory {

    public AccountService acc(){
        return new AccountServiceImpl();
    }
}
