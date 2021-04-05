package clone;
// 关于对象克隆（引用于浅、深拷贝）

import java.util.Date;
import java.util.GregorianCalendar;

public class Employee implements Cloneable{  // 必须实现Cloneable接口
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
        hireDay = new Date();
    }

    public Employee clone() throws CloneNotSupportedException{  // 建议抛出此异常，以告知有无实现cloneable接口
        Employee cloned = (Employee) super.clone();  // 调用实现接口的clone方法进行深拷贝

        cloned.hireDay = (Date) hireDay.clone();

        return cloned;
    }

    public void setHireDay(int year, int month, int day){
        Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();
        hireDay.setTime(newHireDay.getTime());
    }

    public void raiseSalary(double byPercent){
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public String toString(){
        return "Employee[name=" + name + ",salary=" + salary + ",hireDay:" + hireDay + "]";
    }
}
