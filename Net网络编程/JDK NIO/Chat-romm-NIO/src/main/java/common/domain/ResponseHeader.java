package common.domain;

import common.enumeration.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName ResponseHeader
 * @Description 响应消息头部内容
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 11:30
 * @Version 1.0
 **/
@Data
@Builder
public class ResponseHeader {
    private String sender;
    private ResponseType type;
    private Integer responseCode;
    private Long timestamp;
}
