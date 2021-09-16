package com.company.singleTon;

/**
 * 单例模式实现方式二： 延迟加载/“懒汉模式”
 * 优点：实现简单，并节省内存空间
 * 缺点：多线程场景中，此法不能保证单例的状态，不适用。
 *      因为当多个线程并发同时判断instance为空时，就会相应的实例化多个对象。
 */
public class LazySingleton {
    // 自身实例化对象设置为一个属性，用static修饰
    private static LazySingleton instance;

    private LazySingleton(){}

    public static LazySingleton getInstance(){
        if (instance == null)  // 需要用到时再进行实例化
            instance = new LazySingleton();
        return instance;
    }
}
