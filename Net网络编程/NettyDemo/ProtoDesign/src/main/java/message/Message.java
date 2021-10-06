package message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Message
 * @Description Message抽象类
 * @Author Lijuce_K
 * @Date 2021/10/6 0006 15:36
 * @Version 1.0
 **/
@Data
public abstract class Message implements Serializable {
    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    /**
     * 序列号
     */
    private int sequenceId;

    /**
     * 消息类型
     */
    private int messageType;

    /**
     * 待子类实现的抽象函数-获取消息类型
     * @return
     */
    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int PingMessage = 14;
    public static final int PongMessage = 15;


}