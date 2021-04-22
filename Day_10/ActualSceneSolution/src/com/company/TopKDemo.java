package com.company;
/**
 * 实践场景：海量数据寻找TopK的数
 * 直接使用java内置的优先队列实现
 */

import org.junit.Test;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Random;

public class TopKDemo {
    // 模拟海量数据的文件
    private final File file = new File("file" + File.separator + "topkdata.txt");
    private final Random random = new Random();
    private final PriorityQueue<Long> priorityQueue = new PriorityQueue<>(10);

    @Test
    public void computeTokK(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                addToTopKQueue(Long.valueOf(line));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (fileReader != null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
            Long target;
            while ((target = priorityQueue.poll()) != null){
                System.out.println("target = " + target);
            }
        }
    }

    /**
     * 利用java的优先队列数据结构来自动比较大小
     * 小顶堆
     * @param target
     */
    public void addToTopKQueue(Long target){
        if (priorityQueue.size() < 10){
            priorityQueue.add(target);
        }else {
            Long head = priorityQueue.peek();
            if (target > head){
                priorityQueue.poll();
                priorityQueue.add(target);
            }
        }
    }
    @Test
    public void init(){
        long start = System.currentTimeMillis();
        System.out.println("init");
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file, true);
            for (int i = 0; i < 50000000; i++) {
                fileWriter.write(String.valueOf(random.nextLong()) + System.lineSeparator());
            }
            //写入10个接近long的最大值的数，便于取出是验证正确结果
            for (int i = 0; i < 10; i++){
                fileWriter.write(String.valueOf(Long.MAX_VALUE - i) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("用时：" + (System.currentTimeMillis() - start));
    }


}
