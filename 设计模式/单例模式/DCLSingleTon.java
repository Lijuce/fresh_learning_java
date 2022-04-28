package com.company.singleTon;

/**
 * 单例模式实现方式之三：DCL双检查锁机制
 * 优点：内存占用率高，效率高，线程安全，多线程操作原子性
 */
public class DCLSingleTon {
    // 注意此处需要用volatile进行修饰，避免指令重排序而导致instance还未构造即被使用的情况
    private static volatile DCLSingleTon instance;

    private DCLSingleTon(){}

    public static DCLSingleTon getInstance() {
        // 第一次检查instance是否实例化
        if (instance == null){
            synchronized (DCLSingleTon.class){
                // 某线程取得类锁，实例化对象前第二次检查instance是否已被实例化，若没有才最终进行实例化
                if (instance == null)
                    instance = new DCLSingleTon();
            }
        }
        return instance;
    }
}
