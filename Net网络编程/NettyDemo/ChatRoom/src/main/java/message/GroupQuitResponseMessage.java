package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupQuitResponseMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:49
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupQuitResponseMessage extends AbstractResponseMessage {
    public GroupQuitResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupQuitResponseMessage;
    }
}
