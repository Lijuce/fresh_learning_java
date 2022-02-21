package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import message.*;
import protocol.MessageCodec;
import protocol.ProcotolFrameDecoder;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

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
        AtomicBoolean EXIT = new AtomicBoolean(false);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new ProcotolFrameDecoder());
//                    pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    pipeline.addLast(new MessageCodec());
                    // 判断写空闲时间是否超出预期
                    pipeline.addLast(new IdleStateHandler(0, 3, 0));
                    pipeline.addLast(new ChannelDuplexHandler() {
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            if (event.state() == IdleState.WRITER_IDLE) {
//                                System.out.println("发送心跳包至服务端，证明客户端还存活");
                                ctx.writeAndFlush(new PingMessage());
                            }
                        }
                    });
                    pipeline.addLast("Client Handler", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 开新线程用于获取用户输入信息
                            new Thread(() -> {
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
//                                    // 判断是否被中断
//                                    if (Thread.currentThread().isInterrupted()) {
//                                        // 处理中断逻辑
//                                        break;
//                                    }
                                    if (EXIT.get()) {
                                        return;
                                    }
                                    System.out.println("选择下一步操作:");
                                    System.out.println("==================================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================================");
                                    String[] inputStr = scanner.nextLine().split(" ");
                                    String command = inputStr[0];
                                    String toUser;
                                    String content;

                                    String groupName;
                                    switch (command) {
                                        case "send":
                                            toUser = inputStr[1];
                                            content = inputStr[2];
                                            ctx.writeAndFlush(new ChatRequestMessage(content, toUser, username));
                                            break;
                                        case "gcreate":
                                            // 创建群组
                                            groupName = inputStr[1];
                                            List<String> memList = Arrays.asList(inputStr[2].split(","));
                                            Set<String> set = new HashSet<>(memList);
                                            set.add(username); // 将自己加入群聊
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(groupName, set));
                                            break;
                                        case "gsend":
                                            // 发送群聊消息
                                            groupName = inputStr[1];
                                            content = inputStr[2];
                                            ctx.writeAndFlush(new GroupChatRequestMessage(groupName, content, username));
                                            break;
                                        case "gmembers":
                                            // 查看群组成员
                                            groupName = inputStr[1];
                                            ctx.writeAndFlush(new GroupMembersRequestMessage(groupName));
                                            break;
                                        case "gjoin":
                                            // 加入群聊
                                            groupName = inputStr[1];
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(groupName, username));
                                            break;
                                        case "gquit":
                                            // 退出群聊
                                            groupName = inputStr[1];
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(username, groupName));
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
                            if ((msg instanceof LoginResponseMessage)) {
                                LoginResponseMessage responseMessage = (LoginResponseMessage) msg;
                                if (responseMessage.isSuccess()) {
                                    // 登录成功则设置true
                                    LOGIN.set(true);
                                }
                                // 计数减1，唤醒前面的System in线程
                                System.out.println(responseMessage.getReason());
                                WAIT_FOR_LOGIN.countDown();
                            }

                            if (msg instanceof ChatResponseMessage) {
                                ChatResponseMessage chatResponseMessage = (ChatResponseMessage) msg;
                                System.out.println(chatResponseMessage.getFrom() + "回复: " + chatResponseMessage.getContent());
                            }

                            if (msg instanceof GroupChatResponseMessage) {
                                GroupChatResponseMessage message = (GroupChatResponseMessage) msg;
                                System.out.println(message.getFrom() + "群聊消息：" + message.getContent());
                            }

                            if (msg instanceof GroupJoinResponseMessage) {
                                GroupJoinResponseMessage message = (GroupJoinResponseMessage) msg;
                                System.out.println(message.getReason());
                            }

                            if (msg instanceof GroupCreateResponseMessage) {
                                GroupCreateResponseMessage message = (GroupCreateResponseMessage) msg;
                                System.out.println(message.getReason());
                            }

                            if (msg instanceof GroupMembersResponseMessage) {
                                GroupMembersResponseMessage message = (GroupMembersResponseMessage) msg;
                                System.out.print("目前群聊成员有:");
                                System.out.println(message.getMembers());
                            }

                            if (msg instanceof GroupQuitResponseMessage) {
                                GroupQuitResponseMessage message = (GroupQuitResponseMessage) msg;
                                System.out.println(message.getReason());
                            }
                        }

                        // 在连接断开时触发
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            System.out.println("连接已经断开，按任意键退出..");
                            EXIT.set(true);
                        }

                        // 在出现异常时触发
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            System.out.println("连接已经断开，按任意键退出..{}" + cause.getMessage());
                            EXIT.set(true);
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
