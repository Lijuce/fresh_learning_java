package com.company.singleTon;

/**
 * 单例模式实现方式一： 立即加载/“饿汉模式”
 * 优点：实现简单，无多线程同步问题
 * 缺点：由于是静态变量，被创建后一直占用内存空间，某些情况下较为耗费内存。
 */
public class Singleton {
    // 自身实例化对象设置为一个属性，用static、final修饰
    private static final Singleton instance = new Singleton();

    // 构造方法私有化
    private Singleton(){}

    // 外部通过该静态方法调用实例
    public static Singleton getInstance(){
        return instance;
    }
}
