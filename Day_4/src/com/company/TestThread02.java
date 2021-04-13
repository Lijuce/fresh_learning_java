package com.company;
// 2021年4月12日
// 多线程同步下载图片

import com.oracle.webservices.internal.impl.encoding.StreamDecoderImpl;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestThread02 extends Thread{
    private String url;
    private String name;
    public TestThread02(String url, String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public void run(){
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载了图片"+name);
    }

    public static void main(String[] args) {
        TestThread02 t1 = new TestThread02("https://pics2.baidu.com/feed/7dd98d1001e93901788830803eed02ef37d1968b.jpeg?token=0517c96f65c283c1c0aab7244d470304", "图1");
        TestThread02 t2 = new TestThread02("https://pics3.baidu.com/feed/d1a20cf431adcbef8f6d9410e9ae78d5a3cc9f51.jpeg?token=1369351b44be44459cd918010981d882", "图2");
        t1.start();
        t2.start();
    }

}

class WebDownloader{
    public void downloader(String url, String name){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));  // 此处利用别人写好的java包，用于将指定URL资源下载为file文件
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("IO异常");
        }
    }
}

