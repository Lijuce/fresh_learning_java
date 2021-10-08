package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import protocol.MessageCodec;
import protocol.ProcotolFrameDecoder;
import server.handler.*;

/**
 * @ClassName ChatServer
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 10:48
 * @Version 1.0
 **/
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    // 黏包拆包解码器
                    pipeline.addLast(new ProcotolFrameDecoder());
                    // LOG日志
//                    pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    // 此处已经将client发来的消息转化为LoginRequest类型了
                    pipeline.addLast(new MessageCodec());
                    pipeline.addLast(new LoginRequestMessageHandler());
                    pipeline.addLast(new ChatRequestMessageHandler());
                    pipeline.addLast(new GroupCreateRequestMessageHandler());
                    pipeline.addLast(new GroupJoinRequestMessageHandler());
                    pipeline.addLast(new GroupChatRequestMessageHandler());
                    pipeline.addLast(new GroupQuitRequestMessageHandler());
                    pipeline.addLast(new GroupMembersRequestMessageHandler());
                    pipeline.addLast(new QuitHandler());
                }
            });

            Channel channel = serverBootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
