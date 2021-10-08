package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupChatResponseMessage
 * @Description 群聊 响应消息
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:21
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupChatResponseMessage extends AbstractResponseMessage{
    private String from;
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return GroupChatResponseMessage;
    }
}
