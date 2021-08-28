package anonymousInnerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;

public class AnonymousInnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock();
        clock.start(1000, true);
        JOptionPane.showMessageDialog(null, "Quit?");
        System.exit(0);
    }
}

class TalkingClock{
    public void start(int interval, boolean beep){
        ActionListener listener = new ActionListener() { //  此处声明一个类(构造参数)，后面加一大括号，此为匿名内部类
            public void actionPerformed(ActionEvent event) {  // 此处可直接与innerClass/InnerClassTest.java程序的TalkingClock做对比，发现匿名内部类的代码实现更为简洁易懂
                System.out.println("At the tone, the time is " + new Date());
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        };
        Timer t = new Timer(interval, listener);
        t.start();
    }
}
