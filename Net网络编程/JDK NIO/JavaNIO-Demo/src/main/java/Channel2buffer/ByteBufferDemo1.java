package Channel2buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName ByteBufferDemo1
 * @Description 展示ByteBuffer的基本使用示例
 * @Author Lijuce_K
 * @Date 2021/10/9 0009 10:26
 * @Version 1.0
 **/
public class ByteBufferDemo1 {
    public static void main(String[] args) {
        try(FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // ByteBuffer 预先分配内存空间
            // 读一次默认最大为5个字节
            ByteBuffer buffer = ByteBuffer.allocate(5);
            while (true) {
                // 一次读取的字节数，将读取的内容写入buffer中
                int readLen = channel.read(buffer);
                if (readLen == -1) {
                    // 返回-1，说明已读取完毕
                    break;
                }
                // buffer切换至读模式
                buffer.flip();
                // 判断是否还有未读数据
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                // buffer切换至写模式
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
