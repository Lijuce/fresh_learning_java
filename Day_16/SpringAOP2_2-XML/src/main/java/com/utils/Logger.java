package com.utils;

import org.aspectj.lang.ProceedingJoinPoint;

public class Logger {
    public void beforPringLog(){
        System.out.println("前置通知。。。。。。。。。");
    }

    public void afterPringLog(){
        System.out.println("后置通知。。。。。。。。。");
    }

    public void afterThrowingPringLog(){
        System.out.println("异常通知。。。。。。。。");
    }

    public void finalPringLog(){
        System.out.println("最终通知。。。。。。");
    }

//    利用环绕通知，通过手动编码实现动态代理机制
    public Object aroundPringLog(ProceedingJoinPoint pjp){
        Object returnValue = null;
        try {
            Object[] args = pjp.getArgs();  // 获取方法参数
            System.out.println("环绕通知。。。。。。前置");
            returnValue = pjp.proceed(args);  // 明确调用切入点(业务层)方法
            System.out.println("环绕通知。。。。。。后置");
        } catch (Throwable throwable) {
            System.out.println("环绕通知。。。。。。异常");
            throwable.printStackTrace();
        }finally {
            System.out.println("环绕通知。。。。。。最终");
        }
        return returnValue;
    }
}
