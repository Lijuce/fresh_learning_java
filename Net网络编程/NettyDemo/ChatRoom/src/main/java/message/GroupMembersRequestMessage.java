package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupMembersRequestMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:41
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupMembersRequestMessage extends Message{
    private String groupName;

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
