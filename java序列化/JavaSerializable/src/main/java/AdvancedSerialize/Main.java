package AdvancedSerialize;

import GeneralSerialize.User;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * @ClassName Main
 * @Description 利用protostuff进行对象序列化
 * @Author Lijuce_K
 * @Date 2021/7/18 0018 10:23
 * @Version 1.0
 **/
public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setName("Lijuce");
        user.setAge(24);

        // 保存对象，序列化，转化为二进制数据
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        final byte[] protoStuff;
        protoStuff = ProtobufIOUtil.toByteArray(user, schema, buffer);

        // 读取对象，反序列化
        User userObject = schema.newMessage();
        ProtobufIOUtil.mergeFrom(protoStuff, userObject, schema);
        System.out.println(userObject);

    }
}
