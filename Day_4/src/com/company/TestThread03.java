package com.company;
// 2021年4月12日
// 实现接口runnable，重写run方法
public class TestThread03 implements Runnable{
    // 重写run方法
    @Override
    public void run(){
        for (int i = 0; i < 200; i++){
            System.out.println("我在看代码————————————————"+i);
        }
    }

    public static void main(String[] args) {
        TestThread03 testThread03 = new TestThread03();
//        testThread03.start();  // 调用start开启进程
        // 代理方式开启线程（和继承Thread方式的不同之处）
        new Thread(testThread03).start();

        for (int i = 0; i < 200; i++){
            System.out.println("我在学习————————————————" + i);
        }

    }
}
