package com.company.Cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName DebugMethodInterceptor
 * @Description 自定义MethodInterceptor方法拦截器
 * @Author Lijuce_K
 * @Date 2021/8/6 10:17
 * @Version 1.0
 **/
public class DebugMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before ..." + method.getName());
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("After ..." + method.getName());
        return result;
    }
}
