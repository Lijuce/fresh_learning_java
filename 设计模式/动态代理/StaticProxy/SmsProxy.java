package com.company.StaticProxy;

public class SmsProxy implements ISmsService{
    private final ISmsService smsService;

    /**
     * 构造函数创建代理对象
     * @param smsService
     */
    public SmsProxy(ISmsService smsService){
        this.smsService = smsService;
    }

    @Override
    public String send(String message) {
        System.out.println("Before...");
        smsService.send(message);
        System.out.println("After...");
        return null;
    }
}
