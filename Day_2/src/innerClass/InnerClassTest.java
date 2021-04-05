package innerClass;
// 内部类的使用示例
import com.sun.corba.se.impl.ior.TaggedComponentFactoryFinderImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();

        JOptionPane.showMessageDialog(null, "Quit?");
        System.exit(0);

    }
}

class TalkingClock{
    private  int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep){
        this.interval = interval;
        this.beep = beep;
    }

    public void start(){
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    public class TimePrinter implements ActionListener{  // 内部类既可以访问自身的数据域，也可以访问创建它的外围类对象的数据域.
        public void actionPerformed(ActionEvent event){
            System.out.println("At the tone, the time is " + new Date());
            if (beep) Toolkit.getDefaultToolkit().beep();// 例如此处的beep，TimePrinter没有beep变量，却可以访问TalkingClock的beep变量
        }
        // 此处可以这么看：TimePrinter 类没有定义构造器，所以编译器为这个类生成了一个默认的构造器，如下：
        // public TimePrinter(TalkingGock clock){
        // outer = clock;  }
    }
}
