package server.handler.message.impl;

import common.domain.Message;
import common.domain.Response;
import common.domain.ResponseHeader;
import common.domain.Task;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import org.springframework.stereotype.Component;
import server.handler.message.MessageHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName BroadcastMessageHandler
 * @Description 消息广播处理器
 * @Author Lijuce_K
 * @Date 2021/10/25 0025 21:10
 * @Version 1.0
 **/
@Component("MessageHandler.broadcast")
public class BroadcastMessageHandler extends MessageHandler {
    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> tasks, AtomicInteger onlineUsers) {
        try {
            byte[] response = ProtoStuffUtil.serialize(
                    new Response(
                            ResponseHeader.builder()
                                    .type(ResponseType.NORMAL)
                                    .sender(message.getHeader().getSender())
                                    .timestamp(message.getHeader().getTimestamp())
                                    .build(),
                            message.getBody()
                    )
            );
            super.broadcast(server, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
