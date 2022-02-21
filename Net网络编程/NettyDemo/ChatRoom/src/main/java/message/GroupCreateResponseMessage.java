package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupCreateResponseMessage
 * @Description 建立群聊响应消息
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:17
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupCreateResponseMessage extends AbstractResponseMessage {

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupCreateResponseMessage;
    }
}
