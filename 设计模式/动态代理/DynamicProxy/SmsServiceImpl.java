package com.company.DynamicProxy;

import com.company.DynamicProxy.ISmsService;

public class SmsServiceImpl implements ISmsService {
    @Override
    public String send(String message) {
        System.out.println("Send message: " + message);
        return message;
    }
}
