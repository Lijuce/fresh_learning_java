package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.ChatRequestMessage;
import message.ChatResponseMessage;
import server.session.SessionFactory;

import java.util.Objects;

/**
 * @ClassName ChatRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 17:20
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatRequestMessage msg) throws Exception {
        // 发送目标
        String to = msg.getTo();
        // 发送源
        String from = msg.getFrom();
        // 发送内容
        String content = msg.getContent();
        Channel channel = SessionFactory.getSession().getChannel(to);
        if (!Objects.isNull(channel)) {
            // 用户在线
            System.out.println("---------用户在线----------");
            channel.writeAndFlush(new ChatResponseMessage(from, content));
        } else {
            // 用户离线
            System.out.println("---------用户离线----------");
            channelHandlerContext.writeAndFlush(new ChatResponseMessage(false, "对方用户不在线"));
        }
    }
}
