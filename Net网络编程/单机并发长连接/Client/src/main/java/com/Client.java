package com;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName com.Client
 * @Description 客户端
 * @Author Lijuce_K
 * @Date 2021/11/4 0004 14:38
 * @Version 1.0
 **/
public class Client {
    private static final String SERVER_HOST = "127.0.0.1";

    public static void main(String[] args) {
        new Client().start(8000, 8099);
    }

    public void start(final int beginPort, int nPort) {
        System.out.println("客户端已启动");
        EventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

            }
        });

//        bootstrap.connect(SERVER_HOST, 8080);
//        bootstrap.connect(SERVER_HOST, 8081);
        int index = 0;
        int port;
        try {
            while (!Thread.interrupted()) {
                port = beginPort + index;
                ChannelFuture channelFuture = bootstrap.connect(SERVER_HOST, port);
                System.out.println("已连接端口：" + port);
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            System.out.println("连接失败，程序关闭！");
                            System.exit(0);
                        }
                    }
                });
                channelFuture.get();

                if (port == nPort) {
                    index = 0;
                } else {
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
