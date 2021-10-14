package Channel2buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * @ClassName FileChannelDemo
 * @Description FileChannel及其零拷贝方法
 * @Author Lijuce_K
 * @Date 2021/10/9 0009 11:39
 * @Version 1.0
 **/
public class FileChannelDemo {
    public static void main(String[] args) {
        try (
                FileChannel channelFrom = new FileInputStream("data.txt").getChannel();
                FileChannel channelTo = new FileOutputStream("to.txt").getChannel();
        ){
            channelFrom.transferTo(0, channelFrom.size(), channelTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
