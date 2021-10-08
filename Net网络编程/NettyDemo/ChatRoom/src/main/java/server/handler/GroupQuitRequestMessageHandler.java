package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupQuitRequestMessage;
import message.GroupQuitResponseMessage;
import server.session.Group;
import server.session.GroupSessionFactory;

import java.util.Objects;

/**
 * @ClassName GroupQuitRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 14:05
 * @Version 1.0
 **/
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupQuitRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        String username = message.getUsername();

        Group group = GroupSessionFactory.getGroupSession().removeMember(groupName, username);
        if (Objects.nonNull(group)) {
            channelHandlerContext.writeAndFlush(new GroupQuitResponseMessage(true, username + "已退出群聊" + groupName));
        } else {
            channelHandlerContext.writeAndFlush(new GroupQuitResponseMessage(false, groupName + "群不存在"));
        }
    }
}
