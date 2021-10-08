package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupChatRequestMessage;
import message.GroupChatResponseMessage;
import server.session.GroupSessionFactory;

import java.util.List;

/**
 * @ClassName GroupChatRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 11:52
 * @Version 1.0
 **/
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupChatRequestMessage message) throws Exception {
        List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(message.getGroupName());
        for (Channel channel: membersChannel) {
            channel.writeAndFlush(new GroupChatResponseMessage(message.getFrom(), message.getContent()));
        }
    }
}
