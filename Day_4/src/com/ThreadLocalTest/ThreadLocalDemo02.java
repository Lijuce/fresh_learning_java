package com.company.threadlocalTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadLocalDemo02
 * @Description 通过线程池创建日期类对象，为后面的ThreadLocalDateDemo做铺垫
 * @Author Lijuce_K
 * @Date 2021/8/8 0008 16:17
 * @Version 1.0
 **/
public class ThreadLocalDemo02 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            service.submit(
                    () -> {
                        ThreadLocalDemo02 t = new ThreadLocalDemo02();
                        String date = t.getDate(finalI);
                        System.out.println(date);
                    }
            );
            Thread.sleep(1000);
        }
    }

    private String getDate(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }
}
