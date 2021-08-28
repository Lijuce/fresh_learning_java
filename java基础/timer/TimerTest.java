package timer;
/*
接口与回调示例
以定时器Timer为例：
    
*/ 

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;   // 此处导入Timer，是为消除与java.util.Timer的二义性
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerTest {
    public static void main(String[] args) {
        ActionListener listener = new TimerPrinter();
        Timer t = new Timer(1000, listener);  // 传递对象给定时器，而后定时器需知调用哪一个方法
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class  TimerPrinter implements ActionListener{  // 给予定时器识别的类必须实现ActionListener接口
    public void actionPerformed(ActionEvent event){  // 当达到指定时间间隔，即调用此方法actionPerformed
        System.out.println("At the one, the time is " + new Date());
        Toolkit.getDefaultToolkit().beep();

    }
}
