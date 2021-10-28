package common.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ProtoStuffUtil
 * @Description 序列化工具
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 9:09
 * @Version 1.0
 **/
public class ProtoStuffUtil {
    /**
     * 序列化缓存
     */
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    /**
     * 序列化
     * @param obj 序列化对象
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<T> schema;
        try {
            schema = getSchema(cls);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
        return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
    }

    /**
     * 反序列化
     * @param data 待反序列化的字节数据
     * @param cls 反序列化目标类
     * @param <T>
     * @return
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            T message = cls.newInstance();
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }
}
