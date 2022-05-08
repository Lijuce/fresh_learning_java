import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName UnsafeAccessor
 * @Description 自定义的Unsafe对象调用方法
 * @Author Lijuce_K
 * @Date 2022/5/8 17:06
 * @Version 1.0
 **/
public final class UnsafeAccessor {
    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}
