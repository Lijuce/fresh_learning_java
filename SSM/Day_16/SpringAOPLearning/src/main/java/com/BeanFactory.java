package com;

import com.service.IAccountService;
import com.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BeanFactory {

    private IAccountService iAccountService;
//
    private TransactionManager transactionManager;
//
    public void setiAccountService(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }
//
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

//    public static Object getbeanFactory(Object target){
//        return Proxy.newProxyInstance(
//                target.getClass().getClassLoader(),
//                target.getClass().getInterfaces(),
//                new AccountServiceReal(target)
//        );
//    }

        public Object getBeanFactory(){
            return Proxy.newProxyInstance(
                    iAccountService.getClass().getClassLoader(),
                    iAccountService.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Object invoke = null;
                            try {
                                transactionManager.beginTransaction();
                                invoke = method.invoke(iAccountService, args);
                                transactionManager.commit();
                            }catch (Exception e){
                                transactionManager.rollback();
                                e.printStackTrace();
                            }finally {
                                transactionManager.release();
                            }
                            return invoke;
                        }
                    }
            );
        }
}
