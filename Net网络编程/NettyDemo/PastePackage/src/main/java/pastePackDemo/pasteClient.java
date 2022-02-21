package pastePackDemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName pasteClient
 * @Description 展示黏/拆包现象-客户端
 * @Author Lijuce_K
 * @Date 2021/10/5 0005 20:03
 * @Version 1.0
 **/
public class pasteClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrapClient = new Bootstrap()
                .group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                for (int i = 0; i < 10; i++) {
                                    // 一次性发送较少量数据，展示黏包现象
                                    ByteBuf buffer = ctx.alloc().buffer(16);
                                    buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                                    ctx.writeAndFlush(buffer);
                                }
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = bootstrapClient.connect("localhost", 8080);
        channelFuture.channel().closeFuture().sync();
    }
}
