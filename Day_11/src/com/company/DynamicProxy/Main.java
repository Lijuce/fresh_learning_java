package com.company.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @ClassName Main
 * @Description 这种调用代理类的方式是最直接简单的
 * @Author Lijuce_K
 * @Date 2021/8/6 9:59
 * @Version 1.0
 **/
public class Main {
    public static void main(String[] args) {
        ISmsService smsService = new SmsServiceImpl();

        ISmsService proxy = (ISmsService) Proxy.newProxyInstance(
                ISmsService.class.getClassLoader(),
                new Class[]{ISmsService.class},
                new CommonDynamicProxy(smsService)
        );
        proxy.send("hello world");
    }
}
