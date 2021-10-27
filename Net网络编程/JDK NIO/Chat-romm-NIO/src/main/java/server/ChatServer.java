package server;

import common.domain.Message;
import common.domain.Task;
import common.util.ProtoStuffUtil;
import common.util.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import server.handler.message.MessageHandler;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ChatServer
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/15 0015 20:30
 * @Version 1.0
 **/
@Slf4j
public class ChatServer {
    static final int DEFAULT_BUFFER_SIZE = 16;

    private static Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private ExecutorService readPool;
    private ListenerThread listenerThread;
    private BlockingQueue<Task> downloadTaskQueue;
    private AtomicInteger onlineUsers;

    public ChatServer() {
        initServer();
    }
    private void initServer() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            // 切换为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 开始绑定
            serverSocketChannel.bind(new InetSocketAddress(8080));
            // 多路复用选择器创建
            selector = Selector.open();
            // 将 channel 注册至 selector, 并对接收连接事件进行监听
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            onlineUsers = new AtomicInteger(0);
            // 用于读取事件处理的线程池(参数配置)
            this.readPool = new ThreadPoolExecutor(5, 10, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.CallerRunsPolicy());
            this.listenerThread = new ListenerThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launch() {
        new Thread(listenerThread).start();
    }

    private void shutdownServer() {
        try {
            listenerThread.shutdown();
            readPool.shutdown();
            serverSocketChannel.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ListenerThread extends Thread {

        /**
         * 内部调用线程中断接口
         */
        public void shutdown() {
            Thread.currentThread().interrupt();
        }

        @Override
        public void interrupt() {
            try {
                // 先关闭依赖资源
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                super.interrupt();
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // 阻塞监听
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    // 获取选择器中所有注册的监听事件
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 删除已选 key 避免重复处理
                        iterator.remove();
                        // 若触发接收事件
                        if (key.isAcceptable()) {
                            // 交由负责接收事件的处理器处理
                            handleAcceptRequest();
                        } else if (key.isReadable()) {
                            // 读操作处理器
                            // 取消可读触发标记，本次处理后才复位读取标记
                            key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
                            // 交由读取事件的处理器处理
                            readPool.execute(new ReadEventHandler(key));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建立连接请求处理器
     */
    private void handleAcceptRequest() {
        try {
            SocketChannel client = serverSocketChannel.accept();
            // 接收的客户端 channel 同样需设置为非阻塞模式
            client.configureBlocking(false);
            // 绑定 selector 并设置监控客户端读操作是否就绪
            client.register(selector, SelectionKey.OP_READ);
            log.info("服务器正在连接客户端：{}", client.getRemoteAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取事件处理线程
     */
    private class ReadEventHandler implements Runnable {
        private SelectionKey key;
        private SocketChannel client;
        private ByteBuffer buffer;
        private ByteArrayOutputStream baos;

        public ReadEventHandler(SelectionKey key) {
            this.key = key;
            this.client = (SocketChannel) key.channel();
            this.buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            this.baos = new ByteArrayOutputStream();
        }

        @Override
        public void run() {
            try {
                int size;
                while ((size = client.read(buffer)) > 0) {
                    buffer.flip();
                    baos.write(buffer.array(), 0, size);
                    buffer.clear();
                    log.info("读取数据...");
                }
                if (size == -1) {
                    return;
                }
                log.info("读取完毕，继续监听");
                // 继续监听读取事件
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);

                // 唤醒
                key.selector().wakeup();

                byte[] bytes = baos.toByteArray();
                baos.close();
                System.out.println("--------------------接收到的消息-------------");
                System.out.println(bytes.length);

                // 处理响应消息 bytes
                Message message = ProtoStuffUtil.deserialize(bytes, Message.class);
                MessageHandler messageHandler = SpringContextUtil.getBean("MessageHandler", message.getHeader().getType().toString().toLowerCase());
                try {
                    messageHandler.handle(message, selector, key, downloadTaskQueue, onlineUsers);
                } catch (InterruptedException e) {
                    log.error("服务器线程被中断");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        System.out.println("初始化启动中...");
        ChatServer chatServer = new ChatServer();
        chatServer.launch();

        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next.equalsIgnoreCase("QUIT")) {
                System.out.println("服务器准备关闭...");
                chatServer.shutdownServer();
                System.out.println("服务器已成功关闭！");
            }
        }
    }


}
