package message;

import lombok.Data;
import lombok.ToString;

import java.awt.*;

/**
 * @ClassName GroupChatRequestMessage
 * @Description 群聊 请求消息
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:20
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupChatRequestMessage extends Message{
    private String groupName;
    private String content;
    private String from;

    public GroupChatRequestMessage(String groupName, String content, String from) {
        this.groupName = groupName;
        this.content = content;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
