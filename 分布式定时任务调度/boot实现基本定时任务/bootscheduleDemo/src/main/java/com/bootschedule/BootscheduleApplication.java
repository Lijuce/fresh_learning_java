package com.bootschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.staticDemo", "com.multithreadSchDemo"})
public class BootscheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootscheduleApplication.class, args);
    }

}
