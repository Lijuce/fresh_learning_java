package SelectorDemo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName SelectorDemo
 * @Description Selector使用示例
 * @Author Lijuce_K
 * @Date 2021/10/13 0013 10:38
 * @Version 1.0
 **/
public class SelectorDemoServer {
    public static void main(String[] args) throws IOException {
        // 创建新Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 通过 SelectorKey 得知事件和哪个channel的事件关联
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, null);
        // 设置 key 只关注 accept 事件，即只对 accept 事件进行响应
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        // 绑定端口号(默认本地IP localhost)
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("selectionKey" + selectionKey);
        while (true) {
            // select方法用于事件触发。当无事件发生时，线程阻塞；否则线程恢复运行
            selector.select();
            // 处理事件 SelectionKey 内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                /**
                  * 重点：处理 key 时，需从 SelectionKey 集合中将其删除，否则下次触发事件时将出错
                  */
                iter.remove();
                System.out.println("key: " + key);
                // 区分事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    SelectionKey acceptKey = accept.register(selector, 0, null);
                    acceptKey.interestOps(SelectionKey.OP_READ);
                    System.out.println("accept" + accept);
                    System.out.println("acceptKey: " + acceptKey);
                } else if (key.isReadable()) {
                    System.out.println("进入可读状态");
                    // 处理异常断开情况
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        // 正常断开返回 -1
                        int readLen = channel.read(buffer);
                        if (readLen == -1) {
                            key.cancel();
                        } else {
                            // buffer切换至读模式
                            buffer.flip();
                            // 判断是否还有未读数据
                            while (buffer.hasRemaining()) {
                                byte b = buffer.get();
                                System.out.println((char) b);
                            }
                            // buffer切换至写模式
                            buffer.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 由于异常断开造成事件未处理，需主动关闭掉
                        key.cancel();
                        System.out.println("关闭 key");
                    }
                }
            }
        }
    }
}
