package com.company.Cglib;

/**
 * @ClassName IAliSmsService
 * @Description 服务类(无需实现接口)
 * @Author Lijuce_K
 * @Date 2021/8/6 10:24
 * @Version 1.0
 **/
class AliSmsServiceImpl {
    public String send(String msg) {
        System.out.println("Send message: " + msg);
        return msg;
    }
}
