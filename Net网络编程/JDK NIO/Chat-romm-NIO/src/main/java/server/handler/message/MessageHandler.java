package server.handler.message;

import common.domain.Message;
import common.domain.Task;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 21:02
 * @Version 1.0
 **/
public abstract class MessageHandler {
    public static final String SYSTEM_SENDER = "系统提示";

    public abstract void handle(Message message,
                                Selector server,
                                SelectionKey client,
                                BlockingQueue<Task> tasks,
                                AtomicInteger onlineUsers) throws InterruptedException;

    protected void broadcast(Selector server, byte[] data) throws IOException {
        // 此处 keys 和 selectedKeys 有区别
        Set<SelectionKey> selectionKeys = server.keys();
        for (SelectionKey key : selectionKeys) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel) {
                SocketChannel destination = (SocketChannel) channel;
                if (destination.isConnected()) {
                    destination.write(ByteBuffer.wrap(data));
                }
            }
        }
    }
}
