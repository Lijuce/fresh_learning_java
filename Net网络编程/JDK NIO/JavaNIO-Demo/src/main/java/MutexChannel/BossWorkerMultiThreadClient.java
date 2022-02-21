package MutexChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @ClassName BossWorkerMultiThreadClient
 * @Description Boss和Worker关联测试客户端
 * @Author Lijuce_K
 * @Date 2021/10/13 0013 21:52
 * @Version 1.0
 **/
public class BossWorkerMultiThreadClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        socketChannel.write(Charset.defaultCharset().encode("avasdfsdf"));
        System.out.println("connected");
        // 客户端正常断开
        socketChannel.close();
    }
}
