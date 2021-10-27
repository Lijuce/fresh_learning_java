package server.property;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName PromptMsgProperty
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/26 0026 15:30
 * @Version 1.0
 **/
public class PromptMsgProperty {
    public static final String LOGIN_SUCCESS = "登录成功，当前共有%d位在线用户";
    public static final String LOGIN_FAILURE = "用户名或密码错误或重复登录，登录失败";
    public static final String LOGOUT_SUCCESS = "注销成功";
    public static final String RECEIVER_LOGGED_OFF = "接收者不存在或已下线";
    public static final String LOGIN_BROADCAST = "%s用户已上线";
    public static final String LOGOUT_BROADCAST = "%s用户已下线";
    public static final String SERVER_ERROR = "服务器内部出现错误，请重试";
    public static final Charset charset = StandardCharsets.UTF_8;
}
