package message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName LoginRequestMessage
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/6 0006 19:57
 * @Version 1.0
 **/
@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends Message {
    private String username;
    private String password;

    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}

