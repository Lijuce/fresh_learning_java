package com.company;

import com.company.DynamicProxy.JdkProxyFactory;
import com.company.StaticProxy.ISmsService;
import com.company.StaticProxy.SmsProxy;
import com.company.StaticProxy.SmsServiceImpl;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /**
         * 静态代理调用
         */
//        ISmsService smsService = new SmsServiceImpl();
//        SmsProxy smsProxy = new SmsProxy(smsService);
//        smsProxy.send("java");

        /**
         * 动态代理调用
         */
        ISmsService smsService = new SmsServiceImpl();
        ISmsService proxy = (ISmsService) JdkProxyFactory.getProxy(smsService);
        proxy.send("java");
    }
}
