package message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @ClassName GroupCreateRequestMessage
 * @Description 建立群聊请求消息
 * @Author Lijuce_K
 * @Date 2021/10/8 0008 10:03
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends Message{
    private String groupName;
    private Set<String> members;

    public GroupCreateRequestMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
