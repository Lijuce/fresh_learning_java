package server.handler.message.impl;

import common.domain.*;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import server.handler.message.MessageHandler;
import server.property.PromptMsgProperty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName KeepAliveMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/11/3 0003 10:08
 * @Version 1.0
 **/
@Component("MessageHandler.keepalive")
@Slf4j
public class KeepAliveMessageHandler extends MessageHandler {
    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> tasks, AtomicInteger onlineUsers) {
        SocketChannel clientChannel = (SocketChannel) client.channel();
        try {
            byte[] response = ProtoStuffUtil.serialize(
                    new Response(
                        ResponseHeader.builder()
                                .sender(SYSTEM_SENDER)
                                .type(ResponseType.KEEPALIVEMSG)
                                .responseCode(ResponseType.KEEPALIVEMSG.getCode())
                                .build(),
                        "心跳检测成功".getBytes(StandardCharsets.UTF_8)
                    )
            );
            clientChannel.write(ByteBuffer.wrap(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
