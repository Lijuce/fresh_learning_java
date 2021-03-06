package com.company.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonDynamicProxy implements InvocationHandler {
    private final Object target;

    public CommonDynamicProxy(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Before..." + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("After..." + method.getName());
        return result;
    }

}
