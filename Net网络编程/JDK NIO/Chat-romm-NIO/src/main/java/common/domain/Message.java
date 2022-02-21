package common.domain;

import common.enumeration.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Message
 * @Description 自定义消息协议
 * @Author Lijuce_K
 * @Date 2021/10/21 0021 22:05
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * 消息头内容
     */
    private MessageHeader header;

    /**
     * 消息实体内容
     */
    private byte[] body;
}
