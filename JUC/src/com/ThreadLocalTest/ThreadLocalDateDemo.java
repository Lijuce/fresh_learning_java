package com.company.threadlocalTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadLocalDateDemo
 * @Description 使用ThreadLocal使多个线程共享同一个SimpleDateFormat对象
 * @Author Lijuce_K
 * @Date 2021/8/8 0008 16:22
 * @Version 1.0
 **/
public class ThreadLocalDateDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 16; i++) {
            int finalI = i;
            service.submit(
                () -> {
                    ThreadLocalDateDemo threadLocalDemoBeta = new ThreadLocalDateDemo();
                    String date = threadLocalDemoBeta.getDate(finalI);
                    System.out.println(date);
                }
            );
        }
        service.shutdown();
        }

    private String getDate(int seconds) {
        Date date = new Date(1000 * seconds);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        SimpleDateFormat simpleDateFormat = ThreadSaferFormat.dateFormatThreadLocal.get();
        return simpleDateFormat.format(date);
    }
}

class ThreadSaferFormat {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));
}