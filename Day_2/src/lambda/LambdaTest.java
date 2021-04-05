package lambda;

import javax.swing.*;
import java.util.Arrays;
import java.util.Date;
import javax.swing.Timer;

// 学习lambda表达式的使用
// 如何在一个比较器和一个动作监听器中使用 lambda 表达式
public class LambdaTest {

    public static void main(String[] args) {
        String[] planets = new String[] { "Mercury" , "Venus" , "Earth" , "Mars" , "Jupiter" , "Saturn" , "Uranus" , "Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order:");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted by length:");
        Arrays.sort(planets, (first, second) -> first.length() - second.length());  // lambda表达式实现字符串长度比较
        System.out.println(Arrays.toString(planets));

        Timer t = new Timer(1000, event -> System.out.println ("The time is " + new Date()));  // 如果方法只有一个参数， 而且这个参数的类型可以推导得出，那么甚至还可以省略小括号
        // 此处实现Timer功能，相比前面要专门定义类，然后进行实例化等繁琐过程简洁明了了很多。。。

        t.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);

    }
}
