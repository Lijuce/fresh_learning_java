package GeneralSerialize;

/**
 * @ClassName Main
 * @Description 测试类
 * @Author Lijuce_K
 * @Date 2021/7/18 0018 9:51
 * @Version 1.0
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setName("Lijuce");
        user.setAge(25);

        // 保存对象
//        SerializeUtil.saveObject(user);

        // 读取对象
        User userObject;
        userObject = (User) SerializeUtil.readObject();
        System.out.println(userObject);

    }
}
