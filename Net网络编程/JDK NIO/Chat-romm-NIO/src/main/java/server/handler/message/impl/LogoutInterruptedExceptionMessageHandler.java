package server.handler.message.impl;

import common.domain.Message;
import common.domain.Response;
import common.domain.ResponseHeader;
import common.domain.Task;
import common.enumeration.LoginCode;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.handler.message.MessageHandler;
import server.handler.message.impl.LogoutMessageHandler;
import server.property.PromptMsgProperty;
import server.user.UserManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName LogoutInterruptedExceptionHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/11/3 0003 16:55
 * @Version 1.0
 **/
@Component("MessageHandler.logoutinterrupted")
public class LogoutInterruptedExceptionMessageHandler extends MessageHandler {
    @Autowired
    UserManager userManager;

    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> tasks, AtomicInteger onlineUsers) {
        try {
            SocketChannel clientChannel = (SocketChannel) client.channel();
            userManager.logout(clientChannel);

            // 在线用户数量减1
            onlineUsers.decrementAndGet();

            // 用户下线广播
            byte[] logoutBroadcast = ProtoStuffUtil.serialize(
                    new Response(
                            ResponseHeader.builder()
                                    .type(ResponseType.NORMAL)
                                    .sender(SYSTEM_SENDER)
                                    .timestamp(message.getHeader().getTimestamp())
                                    .build(),
                            String.format(PromptMsgProperty.LOGOUT_BROADCAST, message.getHeader().getSender()).getBytes(StandardCharsets.UTF_8)
                    )
            );
            super.broadcast(server, logoutBroadcast);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
