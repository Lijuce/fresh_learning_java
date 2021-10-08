package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupJoinResponseMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:47
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupJoinResponseMessage extends AbstractResponseMessage {

    public GroupJoinResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupJoinResponseMessage;
    }
}

