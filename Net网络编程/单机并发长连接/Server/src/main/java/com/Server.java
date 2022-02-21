package com;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName com.Server
 * @Description 服务器端
 * @Author Lijuce_K
 * @Date 2021/11/4 0004 14:31
 * @Version 1.0
 **/
public class Server {
    public static final int BEGIN_PORT = 8000;
    public static final int N_PORT = 8100;

    public static void main(String[] args) {
        new Server().start(BEGIN_PORT, N_PORT);
    }

    public void start(int beginPort, int nPort) {
        System.out.println("服务端启动中...");

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);

        bootstrap.childHandler(new ConnectionCountHandler());

        for (int i = 0; i <= (nPort - beginPort); i++) {
            final int port = beginPort + i;

            bootstrap.bind(port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    System.out.println("成功绑定监听端口：" + port);
                }
            });
        }
        System.out.println("服务端已启动");
    }

}
