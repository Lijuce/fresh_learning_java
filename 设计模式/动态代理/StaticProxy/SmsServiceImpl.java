package com.company.StaticProxy;

public class SmsServiceImpl implements ISmsService{
    @Override
    public String send(String message) {
        System.out.println("Send message: " + message);
        return message;
    }
}
