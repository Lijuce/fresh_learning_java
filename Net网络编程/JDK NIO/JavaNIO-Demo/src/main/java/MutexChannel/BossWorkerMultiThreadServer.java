package MutexChannel;

import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName BossWorkerServer
 *
 * @Description
 * 演示Boss和Worker两个不同Selector如何进行关联，从而达到多线程执行连接/读写任务的目的
 *
 * @Author Lijuce_K
 * @Date 2021/10/13 0013 21:00
 * @Version 1.0
 **/
public class BossWorkerMultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        // 创建 worker 并进行初始化
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("Worker-" + i);
        }
//        Worker worker = new Worker("Worker-0");

        AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                System.out.println("Boss init...");
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    System.out.println("connected..." + sc.getRemoteAddress());

                    sc.configureBlocking(false);
                    System.out.println("Before init..." + sc.getRemoteAddress());
                    workers[index.getAndIncrement() % workers.length].init(sc);
                    System.out.println(sc.getClass());
                    System.out.println("After init..." + sc.getRemoteAddress());
                }
            }
        }
    }

    /**
     * 用于worker的线程创建
     */
    static class Worker implements Runnable{
        /**
         * 线程安全的队列，用于进行线程间通讯。
         * 此处即用于 boss 和 worker 间的通讯
         */
        ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        /**
         * Worker对应 Selector
         */
        private Selector selector;

        /**
         * Worker 专属线程
         */
        private Thread thread;

        /**
         * 线程名称
         */
        private String name;

        /**
         * 初始化状态，确保仅初始化一次
         */
        private boolean initState = false;

        public Worker(String name) {
            this.name = name;
        }

        /**
         * worker的初始化方法
         */
        public void init(SocketChannel sc) throws IOException {
            // 首次初始化
            if (!initState) {
                thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                initState = true;
            }

            // Worker 内部进行注册
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });

            /**
             * 此处重点之一：wakeup方法，可将select方法造成的阻塞状态更改为非阻塞
             */
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            System.out.println("进入可读状态");
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            int readLen = channel.read(buffer);
                            if (readLen == -1) {
                                // 客户端发起断开连接请求
                                key.cancel();
                            }
                            buffer.flip();
                            while (buffer.hasRemaining()) {
                                byte b = buffer.get();
                                System.out.print((char) b);
                            }
                            buffer.clear();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
