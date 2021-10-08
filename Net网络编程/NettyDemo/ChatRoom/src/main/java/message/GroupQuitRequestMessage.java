package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName GroupQuitRequestMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:46
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends Message {
    private String groupName;

    private String username;

    public GroupQuitRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupQuitRequestMessage;
    }
}
