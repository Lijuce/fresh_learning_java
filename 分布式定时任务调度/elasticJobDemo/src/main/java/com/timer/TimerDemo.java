package com.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName TimerDemo
 * @Description Java原生定时工具Timer的使用Demo
 * @Author Lijuce_K
 * @Date 2021/7/20 9:05
 * @Version 1.0
 **/
public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        timer.scheduleAtFixedRate(new MyTask(), calendar.getTime(), 1000);
    }
}

class MyTask extends TimerTask{
    @Override
    public void run() {
        System.out.println("Execute time start is: " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.scheduledExecutionTime()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
