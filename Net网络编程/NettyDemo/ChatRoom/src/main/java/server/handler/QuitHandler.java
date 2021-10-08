package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.session.SessionFactory;

/**
 * @ClassName QuitHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 15:19
 * @Version 1.0
 **/
public class QuitHandler extends ChannelInboundHandlerAdapter {
    // 连接断开时出发 inactive 事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        System.out.println(ctx.channel() + "已断开连接");
    }

    // 出现异常时出发 exceptionCaught 事件
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        System.out.println(ctx.channel() + "已异常断开");
    }
}
