package server.handler.message.impl;

import common.domain.*;
import common.enumeration.LoginCode;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName LoginMessageHandler
 * @Description 登录消息处理器
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 21:08
 * @Version 1.0
 **/
@Component("MessageHandler.login")
public class LoginMessageHandler extends MessageHandler {

    @Autowired
    private UserManager userManager;

    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> tasks, AtomicInteger onlineUsers) {
        SocketChannel clientChannel = (SocketChannel) client.channel();
        MessageHeader header = message.getHeader();
        String username = header.getSender();
        byte[] body = message.getBody();
        String password = new String(body, Charset.defaultCharset());

        try {
            if (userManager.login(clientChannel, username, password)) {
                // 密码验证完毕，登录成功后需将登录信息进行反馈
                clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(
                        new Response(
                                ResponseHeader.builder()
                                        .type(ResponseType.PROMPT)
                                        .sender(message.getHeader().getSender())
                                        .timestamp(message.getHeader().getTimestamp())
                                        .responseCode(LoginCode.LOGIN_SUCCESS.getCode())
                                        .build(),
                                String.format(PromptMsgProperty.LOGIN_SUCCESS, onlineUsers.incrementAndGet()).getBytes(StandardCharsets.UTF_8)))
                ));

                // 中断一会儿
//                Thread.sleep(10);

                // 登录提示广播
                byte[] loginBroadcast = ProtoStuffUtil.serialize(
                        new Response(
                                ResponseHeader.builder()
                                        .type(ResponseType.PROMPT)
                                        .sender(SYSTEM_SENDER)
                                        .timestamp(message.getHeader().getTimestamp())
                                        .build(),
                                String.format(PromptMsgProperty.LOGIN_BROADCAST, message.getHeader().getSender()).getBytes(StandardCharsets.UTF_8)
                        )
                );
                super.broadcast(server, loginBroadcast);
            } else {
                // 密码验证不通过消息提示
                Response loginFailedMsg = new Response(
                        ResponseHeader.builder()
                        .type(ResponseType.PROMPT)
                        .responseCode(LoginCode.LOGIN_FAILURE.getCode())
                        .sender(message.getHeader().getSender())
                        .timestamp(message.getHeader().getTimestamp())
                        .build(),
                        PromptMsgProperty.LOGIN_FAILURE.getBytes(StandardCharsets.UTF_8)
                );
                byte[] loginFailedMsgSeria = ProtoStuffUtil.serialize(loginFailedMsg);
                clientChannel.write(ByteBuffer.wrap(loginFailedMsgSeria));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
