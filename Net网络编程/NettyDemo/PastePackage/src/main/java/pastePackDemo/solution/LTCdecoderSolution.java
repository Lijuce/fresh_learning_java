package pastePackDemo.solution;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName LTCdecoderSolution
 * @Description LengthFieldBasedFrameDecoder的参数学习使用
 * @Author Lijuce_K
 * @Date 2021/10/5 0005 21:29
 * @Version 1.0
 **/
public class LTCdecoderSolution {
    public static void main(String[] args) {
        // 此处的EmbeddedChannel用来模拟服务端（专门用于练习时简化）
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 5),
                new LoggingHandler(LogLevel.INFO)
        );

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "Hello, world");
        send(buf, "Hi");
        channel.writeInbound(buf);
    }

    private static void send(ByteBuf buf, String content) {
        byte[] bytes = content.getBytes();
        int length = bytes.length;
        // 先写入长度
        buf.writeInt(length);
        // 模拟：协议版本号
        buf.writeByte(1);
        // 再写入内容
        buf.writeBytes(bytes);
    }
}
