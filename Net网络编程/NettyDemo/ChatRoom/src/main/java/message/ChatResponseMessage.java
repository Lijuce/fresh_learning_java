package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName ChatResponseMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 15:11
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class ChatResponseMessage extends AbstractResponseMessage{
    private String from;
    private String content;

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public ChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return ChatResponseMessage;
    }
}
