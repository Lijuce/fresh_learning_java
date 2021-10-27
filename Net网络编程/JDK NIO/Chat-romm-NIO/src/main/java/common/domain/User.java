package common.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * @ClassName User
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/19 0019 11:33
 * @Version 1.0
 **/
@Data
@Builder
public class User implements Serializable {
    private String username;
    private String password;

    private SocketChannel channel;

    public User(String username, String password, SocketChannel channel) {
        this.username = username;
        this.password = password;
        this.channel = channel;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
