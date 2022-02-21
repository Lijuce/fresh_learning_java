package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupJoinRequestMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:45
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupJoinRequestMessage extends Message{
    private String groupName;
    private String username;

    public GroupJoinRequestMessage(String groupName, String username) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
