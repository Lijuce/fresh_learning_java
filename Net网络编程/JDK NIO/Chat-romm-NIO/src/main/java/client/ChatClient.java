package client;

import common.domain.Message;
import common.domain.MessageHeader;
import common.domain.Response;
import common.domain.ResponseHeader;
import common.enumeration.LoginCode;
import common.enumeration.MessageType;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import org.apache.commons.lang.StringUtils;
import server.property.PromptMsgProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName ChatClient
 * @Description 聊天室-客户端(命令行界面)
 * @Author Lijuce_K
 * @Date 2021/10/16 0016 10:49
 * @Version 1.0
 **/
public class ChatClient {
    static final int DEFAULT_BUFFER_SIZE = 1024;

    private Selector selector;
    private SocketChannel clientChannel;
    private ByteBuffer buffer;

    /**
     * 客户端用户名
     */
    private String username;

    /**
     * 记录登录状态
     */
    private boolean isLogin = false;

    /**
     * 记录连接状态
     */
    private boolean isConnected = false;

    /**
     * 退出系统标志
     */
    private static AtomicBoolean EXIT = new AtomicBoolean(false);
    private ReceiverHandle listener;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public ChatClient() {
        initNetwork();
    }

    /**
     * 初始化网络模块
     */
    private void initNetwork() {
        try {
            clientChannel = SocketChannel.open();
            // 先进行连接，再设置非阻塞模式
            clientChannel.connect(new InetSocketAddress("localhost",8080));
            clientChannel.configureBlocking(false);
            selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_READ);
            buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            login();
            isConnected = true;
            EXIT = new AtomicBoolean(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录业务逻辑
     */
    private void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = scanner.nextLine();
        System.out.println("请输入密码");
        String password = scanner.nextLine();

        Message message = new Message(
                MessageHeader.builder()
                        .type(MessageType.LOGIN)
                        .sender(username)
                        .timestamp(System.currentTimeMillis())
                        .build(),
                password.getBytes(PromptMsgProperty.charset));

        try {
            // 将用户信息刷入channel
            byte[] serialize = ProtoStuffUtil.serialize(message);
            clientChannel.write(ByteBuffer.wrap(serialize));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.username = username;
    }

    /**
     * 正式启动，进入监听状态
     */
    public void launch() {
        listener = new ReceiverHandle();
        new Thread(listener).start();
    }

    /**
     * 用于接收信息的线程处理器
     */
    private class ReceiverHandle implements Runnable {
        private boolean connected = true;

        public void shutdown() {
            connected = false;
        }

        @Override
        public void run() {
            try {
                while (connected) {
                    selector.select();
                    int readSize;
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            while ((readSize = clientChannel.read(buffer)) > 0) {
                                buffer.flip();
                                baos.write(buffer.array(), 0, readSize);
                                buffer.clear();
                            }
                            byte[] bytes = baos.toByteArray();
                            baos.close();

                            // 处理接收到的Response响应信息
                            Response response = ProtoStuffUtil.deserialize(bytes, Response.class);
                            handleResponse(response);
                            countDownLatch.countDown();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("服务器关闭，请重新尝试连接");
//                e.printStackTrace();
                isLogin = false;
            }
        }

        private void handleResponse(Response response) {
            // 详细处理response
            ResponseHeader header = response.getHeader();
            switch (header.getType()) {
                // 系统提示
                case PROMPT:
                    if (header.getResponseCode() != null) {
                        Integer responseCode = header.getResponseCode();
                        LoginCode loginCode = LoginCode.fromCode(responseCode);

                        // 判断为登录操作
                        if (loginCode == LoginCode.LOGIN_SUCCESS) {
                            isLogin = true;
                            System.out.println("登录成功");
                        } else if (loginCode == LoginCode.LOGOUT_SUCCESS) {
                            System.out.println("下线成功");
                            break;
                        }
                    }
                    String info = new String(response.getBody(), Charset.defaultCharset());
                    System.out.println("-------------------------------系统提示-------------------------------");
                    System.out.println(info);
                    System.out.println("---------------------------------------------------------------------");
                    break;
                // 聊天消息
                case NORMAL:
                    System.out.println("-------------------聊天消息-----------------");
                    System.out.print(response.getHeader().getSender() + ": ");
                    System.out.println(new String(response.getBody(), Charset.defaultCharset()));
                    System.out.println("-------------------------------------------");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 用户消息发送接口
     * @param content 所发送的消息内容
     */
    public void send(String content) {
        if (!isLogin) {
            System.out.println("尚未登录");
            return;
        }
        try {
            Message message;
            if (content.startsWith("@")) {
                // 单聊模式发送消息
                // 以空格为界限
                String[] slices = content.split(" ");
                String receiver = slices[0].substring(1);
                message = new Message(
                        MessageHeader.builder()
                            .type(MessageType.NORMAL)
                            .sender(username)
                            .receiver(receiver)
                            .timestamp(System.currentTimeMillis())
                            .build(),
                        slices[1].getBytes(StandardCharsets.UTF_8)
                );
            } else {
                // 广播模式发送消息
                message = new Message(
                        MessageHeader.builder()
                                .type(MessageType.BROADCAST)
                                .sender(username)
                                .timestamp(System.currentTimeMillis())
                                .build(),
                        content.getBytes(StandardCharsets.UTF_8)
                );
            }
            clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端断开连接接口
     */
    public void disConnect() {
        try {
            logout();
            // 本身未成功建立连接，则直接返回。
            if (!isConnected) {
                return;
            }
            listener.shutdown();
            // 发送消息后稍等再断开，避免最后的消息无法送达
            Thread.sleep(10);
            clientChannel.socket().close();
            clientChannel.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端用户退出登录
     */
    private void logout() {
        // 本身没有登录，则直接返回。
        if (!isLogin) {
            return;
        }
        System.out.println("客户端发送下线请求");
        Message message = new Message(
                MessageHeader.builder()
                    .type(MessageType.LOGOUT)
                    .sender(username)
                    .timestamp(System.currentTimeMillis())
                    .build(),
                null
        );

        try {
            clientChannel.write(ByteBuffer.wrap(ProtoStuffUtil.serialize(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("客户端初始化...");
        ChatClient chatClient = new ChatClient();
        chatClient.launch();

        new Thread(() -> {
            Scanner scanner;
            while (true) {
                if (EXIT.get()) {
                    break;
                }

                try {
                    // 用于System.in和NIO线程间通讯
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countDownLatch = new CountDownLatch(1);

                if (!chatClient.isLogin) {
//                countDownLatch = new CountDownLatch(1);
                    System.out.println("用户名或密码错误，输入Y/y再次尝试登录，点击任意键退出...");
                    String tryLogin = new Scanner(System.in).nextLine();
                    if (StringUtils.equals("y", tryLogin) || StringUtils.equals("Y", tryLogin)) {
                        chatClient.login();
                        continue;
                    }
                    System.out.println("退出系统中...");
                    break;
                }


                System.out.println("--------------请输入操作指令------------------------");
                System.out.println("         (1)群聊send:[消息内容]");
                System.out.println("         (2)单聊send:@[用户名][消息内容]");
                System.out.println("         (3)退出用户quit");
                System.out.println("--------------------------------------------------");
                scanner = new Scanner(System.in);
                String[] command = scanner.nextLine().split(":");
                switch (command[0]) {
                    case "send":
                        String content = command[1];
                        chatClient.send(content);
                        break;
                    case "quit":
                        System.out.println("退出账户");
                        EXIT.set(true);
                        break;
                    default:
                        break;
                }
            }
            chatClient.disConnect();
            System.exit(0);
        }, "System in").start();
    }
}
