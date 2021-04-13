package com.company;

public class TestThread04 implements Runnable{
    @Override
    public void run(){
        while (true){
            System.out.println("买到票数");
        }
    }
}
