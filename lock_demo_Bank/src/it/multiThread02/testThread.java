package it.multiThread02;

public class testThread implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i < 200; i++) {
            System.out.println("我在看代码。。。。。。。。。。。");
        }
    }

    public static void main(String[] args) {
        testThread testThread = new testThread();
        new Thread(testThread).start();

        for (int i = 0; i < 200; i++){
            System.out.println("我在学习————————————————" + i);
        }
    }
}
