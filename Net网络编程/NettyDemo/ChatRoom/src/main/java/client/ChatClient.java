package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import message.ChatRequestMessage;
import message.ChatResponseMessage;
import message.LoginRequestMessage;
import message.LoginResponseMessage;
import protocol.MessageCodec;
import protocol.ProcotolFrameDecoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName ChatClient
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 10:30
 * @Version 1.0
 **/
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        // countDownLatch用于线程间通讯
        CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
        // 用原子类型搭配countDownLatch使用
        AtomicBoolean LOGIN = new AtomicBoolean(false);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new ProcotolFrameDecoder());
                    pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    pipeline.addLast(new MessageCodec());
                    pipeline.addLast("Client Handler", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 开新线程用于获取用户输入信息
                            new Thread(()-> {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名:");
                                String username = scanner.nextLine();
                                System.out.println("请输入密码:");
                                String password = scanner.nextLine();
                                LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
                                ctx.writeAndFlush(loginRequestMessage);

                                System.out.println("等待后续操作...");
                                try {
                                    WAIT_FOR_LOGIN.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // 登录失败情况
                                if (!LOGIN.get()) {
                                    ctx.channel().close();
                                    return;
                                }

                                // 登录成功情况
                                while (true) {
                                    System.out.println("选择下一步操作:");
                                    System.out.println("==================================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================================");
                                    String[] inputStr = scanner.nextLine().split(" ");
                                    String command = inputStr[0];
                                    switch (command) {
                                        case "send":
                                            String toUser = inputStr[1];
                                            String content = inputStr[2];
                                            ctx.writeAndFlush(new ChatRequestMessage(content, toUser, username));
                                            break;
                                        case "quit":
                                            ctx.channel().close();
                                            return;
                                    }
                                }

                            }, "System in").start();
                        }

                        // 注意：此处是NIO线程，和上面的System in是两个不同的线程
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            System.out.println("读取消息：" + msg);
                            if ((msg instanceof LoginResponseMessage)) {
                                LoginResponseMessage responseMessage = (LoginResponseMessage) msg;
                                if (responseMessage.isSuccess()) {
                                    // 登录成功则设置true
                                    LOGIN.set(true);
                                }
                                // 计数减1，唤醒前面的System in线程
                                System.out.println("读取消息：" + msg);
                                WAIT_FOR_LOGIN.countDown();
                            }

                            if (msg instanceof ChatResponseMessage) {
                                ChatResponseMessage chatResponseMessage = (ChatResponseMessage) msg;
                                System.out.println("回复: " + chatResponseMessage.getContent());
                            }
                        }
                    });
                }
            });

            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
