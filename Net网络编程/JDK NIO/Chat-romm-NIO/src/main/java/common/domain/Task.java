package common.domain;

import common.enumeration.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.channels.SocketChannel;

/**
 * @ClassName Task
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 21:05
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private SocketChannel receiver;
    private TaskType type;
    private String desc;
    private Message message;
}
