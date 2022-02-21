package message;

import lombok.Data;

/**
 * @ClassName ChatRequestMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 15:07
 * @Version 1.0
 **/
@Data
public class ChatRequestMessage extends Message{
    private String content;
    private String to;
    private String from;

    public ChatRequestMessage( ) {

    }

    public ChatRequestMessage(String content, String to, String from) {
        this.content = content;
        this.to = to;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
