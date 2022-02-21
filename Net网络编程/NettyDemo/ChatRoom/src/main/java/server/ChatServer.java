package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
                    // 检测读空闲时间是否超出预期
                    pipeline.addLast(new IdleStateHandler(5,0,0));
                    pipeline.addLast(new ChannelDuplexHandler() {
                        // 可用于触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            if (event.state() == IdleState.READER_IDLE) {
//                                System.out.println("已 5s 未读到数据");
                                ctx.channel().close();
                            }
                        }
                    });
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
