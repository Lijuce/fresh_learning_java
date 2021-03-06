package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.LoginRequestMessage;
import message.LoginResponseMessage;
import server.service.UserServiceFactory;
import server.session.SessionFactory;

import java.util.Objects;

/**
 * @ClassName LoginRequestMessageHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 17:19
 * @Version 1.0
 **/
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        String username = loginRequestMessage.getUsername();
        String password = loginRequestMessage.getPassword();
        LoginResponseMessage loginResponseMessage;

        // 验证用户及对应密码
        boolean login = UserServiceFactory.getUserService().login(username, password);
        // 判断用户是否重复登录
        Channel channel = SessionFactory.getSession().getChannel(username);
        if (login) {
            if (Objects.nonNull(channel)) {
                loginResponseMessage = new LoginResponseMessage(false, "用户已存在，请勿重复登录");
            } else {
                // 验证成功后，将username和当前channel进行绑定
                SessionFactory.getSession().bind(channelHandlerContext.channel(), username);
                loginResponseMessage = new LoginResponseMessage(true, "登录成功");
            }
        } else {
            loginResponseMessage = new LoginResponseMessage(false, "登录失败");
        }
        channelHandlerContext.writeAndFlush(loginResponseMessage);
    }
}
