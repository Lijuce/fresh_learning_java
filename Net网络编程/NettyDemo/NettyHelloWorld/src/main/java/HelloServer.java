import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName HelloServer
 * @Description Netty的helloworld Demo 服务端
 * @Author Lijuce_K
 * @Date 2021/10/6 0006 16:09
 * @Version 1.0
 **/
public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器，负责组装Netty组件，并启动服务器
        new ServerBootstrap()
                // 2. 事件轮询组，由于是服务端，因此包括BossEventLoop 和 WorkerEventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的ServerSocketChannel实现方式如NIO、BIO
                .channel(NioServerSocketChannel.class)
                // 4. boss负责处理连接 worker   负责处理读写，决定worker执行的操作
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 5. channel 代表和客户端进行数据读写的通道 初始化，负责添加handler
                    // 此处是执行业务逻辑的主要阵地
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 解码ByteBuf
                        socketChannel.pipeline().addLast(new StringDecoder());
                        // 主要逻辑处理
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
