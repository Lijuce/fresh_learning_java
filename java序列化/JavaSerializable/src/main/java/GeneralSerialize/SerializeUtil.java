package GeneralSerialize;

import java.io.*;

/**
 * @ClassName SerializeUtil
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/7/18 0018 9:45
 * @Version 1.0
 **/
public class SerializeUtil {
    /**
     * 序列化，保存对象
     * @param object
     * @throws Exception
     */
    public static void saveObject(Object object) throws Exception {
        ObjectOutputStream out = null;
        FileOutputStream fout = null;

        try {
            fout = new FileOutputStream("user.txt");
            out = new ObjectOutputStream(fout);
            out.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            fout.close();
            out.close();
        }
    }

    /**
     * 反序列化，读取对象
     * @return
     * @throws Exception
     */
    public static Object readObject() throws Exception {
        ObjectInputStream in = null;
        FileInputStream fin = null;
        Object outputObject = null;
        try {
            fin = new FileInputStream("user.txt");
            in = new ObjectInputStream(fin);
            outputObject = in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            fin.close();
            in.close();
        }
        return outputObject;
    }
}
