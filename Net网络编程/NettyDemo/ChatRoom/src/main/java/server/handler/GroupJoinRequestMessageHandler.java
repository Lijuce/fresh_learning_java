package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupChatResponseMessage;
import message.GroupJoinRequestMessage;
import message.GroupJoinResponseMessage;
import server.session.Group;
import server.session.GroupSessionFactory;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName GroupJoinRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 11:21
 * @Version 1.0
 **/
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupJoinRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        Group group = GroupSessionFactory.getGroupSession().joinMember(groupName, message.getUsername());
        List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        if (Objects.nonNull(group)) {
            channelHandlerContext.writeAndFlush(new GroupJoinResponseMessage(true, "成功加入群聊"+groupName));
            for (Channel channel : membersChannel) {
                channel.writeAndFlush(new GroupChatResponseMessage(message.getUsername(), message.getUsername() + "加入群聊"));
            }
        } else {
            channelHandlerContext.writeAndFlush(new GroupJoinResponseMessage(false, groupName + "该群不存在"));
        }
    }
}
