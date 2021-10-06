import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
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
                new MessageCodec());

        // encode编码测试
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("zhangsan", "123");
        // 出站
        channel.writeOutbound(loginRequestMessage);

        // decode解码测试
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, loginRequestMessage, buf);
        // 入站
        channel.writeInbound(buf);
    }
}
