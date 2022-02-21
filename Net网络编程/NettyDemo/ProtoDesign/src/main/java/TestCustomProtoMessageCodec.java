import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import message.LoginRequestMessage;
import protocol.MessageCodec;

/**
 * @ClassName TestCustomProto
 * @Description 测试自定义的消息协议
 *              用来观察消息编解码后的详细情况
 * @Author Lijuce_K
 * @Date 2021/10/6 0006 16:41
 * @Version 1.0
 **/
public class TestCustomProtoMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.INFO),
                // 此处添加黏拆包解码器
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec());

        // encode编码测试
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("zhangsan", "123");
        // 出站
        channel.writeOutbound(loginRequestMessage);

        // decode解码测试
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, loginRequestMessage, buf);

        // 测试黏包拆包问题
        // 先通过slice方法人为制造拆包现象
        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes()-100);
        // 引用计数 +1
        s1.retain();

//        // 入站
//        channel.writeInbound(buf);
        // 由于入栈会进行引用计数 -1 操作，因此上面需要进行 +1 操作
        channel.writeInbound(s1);
        channel.writeInbound(s2);

    }
}
