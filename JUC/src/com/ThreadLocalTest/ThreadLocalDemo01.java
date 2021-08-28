package com.company.threadlocalTest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName ThreadLocalDemo01
 * @Description 多线程创建日期类Demo示例，为后面的ThreadLocalDemo02做铺垫
 * @Author Lijuce_K
 * @Date 2021/8/8 0008 16:09
 * @Version 1.0
 **/
public class ThreadLocalDemo01 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        String date = new ThreadLocalDemo01().date(finalI);
                        System.out.println(date);
                    }
            ).start();
            Thread.sleep(1000);
        }
    }

    private String date(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }
}
