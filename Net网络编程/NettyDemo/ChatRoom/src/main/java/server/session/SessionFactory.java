package server.session;

/**
 * @ClassName SessionFactory
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/10/7 0007 16:37
 * @Version 1.0
 **/
public abstract class SessionFactory {

    private static Session session = new SessionMemoryImpl();

    public static Session getSession() {
        return session;
    }
}