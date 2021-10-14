package SelectorDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @ClassName SelectorDemoClient
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/13 0013 11:00
 * @Version 1.0
 **/
public class SelectorDemoClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("connected");
        // 客户端正常断开
//        socketChannel.close();
    }
}
