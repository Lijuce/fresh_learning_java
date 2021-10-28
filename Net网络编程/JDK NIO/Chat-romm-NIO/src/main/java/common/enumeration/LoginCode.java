package common.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginCode
 * @Description 登录状态
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 15:52
 * @Version 1.0
 **/
public enum LoginCode {
    LOGIN_SUCCESS(1, "登录成功"),
    LOGIN_FAILURE(2, "登录失败"),
    LOGOUT_SUCCESS(3, "下线成功");

    private int code;
    private String desc;
    private static Map<Integer, LoginCode> map = new HashMap<>();


    static {
        for (LoginCode code: values()) {
            map.put(code.getCode(), code);
        }
    }

    public static LoginCode fromCode(int code) {
        return map.get(code);
    }

    LoginCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
