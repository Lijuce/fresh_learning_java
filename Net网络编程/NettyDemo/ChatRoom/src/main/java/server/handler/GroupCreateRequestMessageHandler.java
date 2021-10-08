package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupCreateRequestMessage;
import message.GroupCreateResponseMessage;
import server.session.Group;
import server.session.GroupSession;
import server.session.GroupSessionFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName GroupCreateRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 11:19
 * @Version 1.0
 **/
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupCreateRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        Set<String> members = message.getMembers();

        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (Objects.isNull(group)) {
            // 开始建立新群聊
            // 响应拉群成功消息
            channelHandlerContext.writeAndFlush(new GroupCreateResponseMessage(true, "成功创建新群聊" + groupName));
            // 发送拉群消息
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            for (Channel channel : membersChannel) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入群" + groupName));
            }
        } else {
            // 响应拉群失败消息
            channelHandlerContext.writeAndFlush(new GroupCreateResponseMessage(false, groupName + "该群已存在！"));
        }
    }
}
