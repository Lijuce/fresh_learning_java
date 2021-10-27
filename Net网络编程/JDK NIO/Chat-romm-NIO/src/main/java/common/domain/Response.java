package common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Response
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 11:29
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private ResponseHeader header;
    private byte[] body;
}
