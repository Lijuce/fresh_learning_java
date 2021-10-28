package server.handler.message.impl;

import common.domain.*;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.handler.message.MessageHandler;
import server.property.PromptMsgProperty;
import server.user.UserManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName NormalMessageHandler
 * @Description 普通单聊消息处理器
 * @Author Lijuce_K
 * @Date 2021/10/25 0025 21:44
 * @Version 1.0
 **/
@Component("MessageHandler.normal")
@Slf4j
public class NormalMessageHandler extends MessageHandler {
    @Autowired
    UserManager userManager;

    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> tasks, AtomicInteger onlineUsers) {
        SocketChannel clientChannel = (SocketChannel) client.channel();
        MessageHeader header = message.getHeader();
        // 接收者
        SocketChannel receiverChannel = userManager.getUserChannel(header.getReceiver());
        try {
            if (Objects.isNull(receiverChannel)) {
                byte[] response = ProtoStuffUtil.serialize(
                        new Response(
                                ResponseHeader.builder()
                                        .type(ResponseType.PROMPT)
                                        .sender(SYSTEM_SENDER)
                                        .timestamp(message.getHeader().getTimestamp())
                                        .build(),
                                PromptMsgProperty.RECEIVER_LOGGED_OFF.getBytes(StandardCharsets.UTF_8)
                        )
                );
                clientChannel.write(ByteBuffer.wrap(response));
            } else {
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
                log.info("已转发给{}", receiverChannel);
                receiverChannel.write(ByteBuffer.wrap(response));
                // 同时告知客户端本身相关提示
                clientChannel.write(ByteBuffer.wrap(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
