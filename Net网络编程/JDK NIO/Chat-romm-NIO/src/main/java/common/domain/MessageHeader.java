package common.domain;

import common.enumeration.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * @ClassName MessageHeader
 * @Description 自定义消息协议的头部实体内容
 * @Author Lijuce_K
 * @Date 2021/10/21 0021 22:07
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  // 简单对象构建及赋值操作的代码
public class MessageHeader {
    /**
     * 消息发送者
     */
    private String sender;

    /**
     * 消息接收者
     */
    private String receiver;

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 时间戳
     */
    private Long timestamp;

}
