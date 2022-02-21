package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupMembersRequestMessage;
import message.GroupMembersResponseMessage;
import server.session.GroupSessionFactory;

import java.util.Set;

/**
 * @ClassName GroupMembersRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 14:17
 * @Version 1.0
 **/
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMembersRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        Set<String> members = GroupSessionFactory.getGroupSession().getMembers(groupName);

        channelHandlerContext.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}
