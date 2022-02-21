package message;

/**
 * @ClassName PingMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 16:11
 * @Version 1.0
 **/
public class PingMessage extends Message{
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
