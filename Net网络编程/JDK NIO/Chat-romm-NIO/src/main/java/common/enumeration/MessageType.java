package common.enumeration;

/**
 * @ClassName MessageType
 * @Description 自定义消息类型
 * @Author Lijuce_K
 * @Date 2021/10/21 0021 22:08
 * @Version 1.0
 **/
public enum MessageType {
    LOGIN(1, "登录"),
    LOGOUT(2, "注销"),
    NORMAL(3, "单聊"),
    BROADCAST(4, "群发"),
    TASK(4, "任务");

    private int code;
    private String desc;

    MessageType(int code, String desc) {
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
