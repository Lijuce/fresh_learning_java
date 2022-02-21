package server.user;

import common.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserManager
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/19 0019 11:29
 * @Version 1.0
 **/
@Component("userManager")
@Slf4j
public class UserManager {
    private Map<String, User> users;

    private Map<SocketChannel, String> onlineUsers;

    public UserManager() {
        users = new ConcurrentHashMap<>();
        users.put("user1", User.builder().username("user1").password("pwd1").build());
        users.put("user2", User.builder().username("user2").password("pwd2").build());
        users.put("user3", User.builder().username("user3").password("pwd3").build());
        onlineUsers = new ConcurrentHashMap<>();
    }

    /**
     * 用户登录接口 (注意线程安全)
     * @param channel
     * @param username
     * @param password
     * @return
     */
    public synchronized boolean login(SocketChannel channel, String username, String password) {
        // 用户不存在情况
        if (!users.containsKey(username)) {
            return false;
        }
        User user = users.get(username);
        // 密码错误情况
        if (!user.getPassword().equals(password)) {
            return false;
        }
        // 重复登录情况
        if (user.getChannel() != null) {
            log.info("重复登录，拒绝！");
            return false;
        }
        // 记录成功登录的用户
        user.setChannel(channel);
        onlineUsers.put(channel, username);
        return true;
    }

    /**
     * 用户登出接口 (注意线程安全)
     * @param channel
     */
    public synchronized void logout(SocketChannel channel) {
        String logoutUsername = onlineUsers.get(channel);
        log.info("{}下线", logoutUsername);
        // 下线的用户，需释放其channel
        users.get(logoutUsername).setChannel(null);
        onlineUsers.remove(channel);
    }

    /**
     * 获取指定用户的channel
     * @param username
     * @return
     */
    public synchronized SocketChannel getUserChannel(String username) {
        User user = users.get(username);
        if (Objects.isNull(user)) {
            return null;
        }

        SocketChannel lastLoginChannel = user.getChannel();
        if (Objects.nonNull(lastLoginChannel) && onlineUsers.containsKey(lastLoginChannel)) {
            return lastLoginChannel;
        } else {
            return null;
        }
    }


}
