package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupJoinRequestMessage;
import message.GroupJoinResponseMessage;
import server.session.Group;
import server.session.GroupSessionFactory;

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
        if (Objects.nonNull(group)) {
            channelHandlerContext.writeAndFlush(new GroupJoinResponseMessage(true, "成功加入群聊"+groupName));
        } else {
            channelHandlerContext.writeAndFlush(new GroupJoinResponseMessage(false, groupName + "该群不存在"));
        }
    }
}
