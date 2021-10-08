package message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @ClassName GroupMembersResponseMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 14:19
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupMembersResponseMessage extends Message {

    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
