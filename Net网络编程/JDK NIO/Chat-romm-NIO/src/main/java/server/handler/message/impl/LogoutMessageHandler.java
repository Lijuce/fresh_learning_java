package server.handler.message.impl;

import common.domain.Message;
import common.domain.Response;
import common.domain.ResponseHeader;
import common.domain.Task;
import common.enumeration.LoginCode;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.handler.message.MessageHandler;
import server.user.UserManager;

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
 * @ClassName LogoutMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/26 0026 9:18
 * @Version 1.0
 **/
@Component("MessageHandler.logout")
@Slf4j
public class LogoutMessageHandler extends MessageHandler {
    @Autowired
    UserManager userManager;

    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> tasks, AtomicInteger onlineUsers) throws InterruptedException {
        try {
            SocketChannel clientChannel = (SocketChannel) client.channel();
            userManager.logout(clientChannel);
            byte[] response = ProtoStuffUtil.serialize(
                    new Response(
                            ResponseHeader.builder()
                                    .type(ResponseType.PROMPT)
                                    .responseCode(LoginCode.LOGOUT_SUCCESS.getCode())
                                    .sender(message.getHeader().getSender())
                                    .timestamp(message.getHeader().getTimestamp())
                                    .build(),
                            "注销成功".getBytes(StandardCharsets.UTF_8)
                    )
            );
            clientChannel.write(ByteBuffer.wrap(response));
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
                            String.format("%s用户已下线", message.getHeader().getSender()).getBytes(StandardCharsets.UTF_8)
                    )
            );
            super.broadcast(server, logoutBroadcast);
            log.info("客户端退出");
            // 对接下来触发的事件无需处理，因此应当取消
            client.cancel();
            clientChannel.close();
            clientChannel.socket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
