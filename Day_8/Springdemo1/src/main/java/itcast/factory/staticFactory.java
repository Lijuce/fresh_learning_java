package itcast.factory;

import itcast.service.impl.AccountService;
import itcast.service.impl.AccountServiceImpl;

public class staticFactory {

    public static AccountService acc()
    {
        return new AccountServiceImpl();

    }
}
