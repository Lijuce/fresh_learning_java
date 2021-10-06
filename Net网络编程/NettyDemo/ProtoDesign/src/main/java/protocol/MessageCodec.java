package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import message.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @ClassName MeesageCodec
 * @Description 自定义协议的编解码器
 * @Author Lijuce_K
 * @Date 2021/10/6 0006 16:42
 * @Version 1.0
 **/
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    public void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
        // 1. 魔数-确定数据包是否有效(4个字节)
        // 此处随便定义了
        out.writeBytes(new byte[] {1,2,3,4});
        // 2. 版本号-协议的升级版本(1个字节)
        out.writeByte(1);
        // 3. 序列化算法-如JDK、protobuf、json等(1个字节)
        // 此处为了简化，0代表使用JDK序列化，1表使用protobuf序列化
        out.writeByte(0);
        // 4. 指令(消息)类型-如登录、注册等业务相关的类型(1个字节)
        out.writeByte(message.getMessageType());
        // 5. 请求序号-双工通信，提供异步能力(4个字节)
        out.writeInt(message.getSequenceId());
        // 无意义-仅为了对齐填充，使整个字节数为2的幂次方
        out.writeByte(0xff);
        // 6. 消息内容序列化
        // 获取内容字节数组,将对象转化为字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();
        // 7. 正文长度
        out.writeInt(bytes.length);
        // 写入内容
        out.writeBytes(bytes);
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> outList) throws Exception {
        // 魔数
        int magicNum = in.readInt();
        // 版本号
        byte version = in.readByte();
        // 序列化算法
        byte serializerType = in.readByte();
        // 消息类型
        byte messageType = in.readByte();
        // 序列号
        int sequenceId = in.readInt();
        // 无意义的对齐字节数组
        in.readByte();
        // 正文内容解码
        // 正文长度
        int len = in.readInt();
        byte[] bytes = new byte[len];
        in.readBytes(bytes, 0, len);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) objectInputStream.readObject();

        System.out.println(message);
        outList.add(message);
    }

}
