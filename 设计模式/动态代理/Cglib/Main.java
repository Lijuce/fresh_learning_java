package com.company.Cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/8/6 10:19
 * @Version 1.0
 **/
public class Main {
    public static void main(String[] args) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(AliSmsServiceImpl.class.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(AliSmsServiceImpl.class);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        // 创建代理类
        AliSmsServiceImpl result = (AliSmsServiceImpl) enhancer.create();

        result.send("hello");
    }
}
