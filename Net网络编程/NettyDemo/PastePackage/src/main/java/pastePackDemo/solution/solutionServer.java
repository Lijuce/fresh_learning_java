package pastePackDemo.solution;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName fixedLengthSolutionServer
 * @Description 定长解码器 / 分隔符解码器
 * @Author Lijuce_K
 * @Date 2021/10/5 0005 20:44
 * @Version 1.0
 **/
public class solutionServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            // 关键之处：FixedLengthFrameDecoder 定长解码器
                            nioSocketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));
                            // LineBasedFrameDecoder 换行符作为分隔符的解码器，1024为寻找换行符过程所能遍历的最大长度
                            nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
