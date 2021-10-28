package server.exception.handler;

import common.domain.Message;
import common.domain.Response;
import common.domain.ResponseHeader;
import common.enumeration.ResponseType;
import common.util.ProtoStuffUtil;
import org.springframework.stereotype.Component;
import server.property.PromptMsgProperty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName InterruptedExceptionHandler
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/28 0028 10:32
 * @Version 1.0
 **/
@Component("interruptedExceptionHandler")
public class InterruptedExceptionHandler {

    public void handleException(SocketChannel channel, Message message) {
        try {
            byte[] response = ProtoStuffUtil.serialize(
                    new Response(
                            ResponseHeader.builder()
                                    .type(ResponseType.PROMPT)
                                    .sender(message.getHeader().getSender())
                                    .timestamp(message.getHeader().getTimestamp())
                                    .build(),
                            PromptMsgProperty.SERVER_ERROR.getBytes(StandardCharsets.UTF_8)
                    )
            );
            channel.write(ByteBuffer.wrap(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
