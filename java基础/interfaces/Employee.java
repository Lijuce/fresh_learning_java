package interfaces;

public class Employee implements Comparable<Employee>{  // 必须实现接口Comparable
    private String name;
    private double salary;

    public Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
    }

    public String getName(){
        return name;
    }

    public double getSalary(){
        return salary;
    }

    public void raiseSalary(double byPercent){
        double raise = salary * byPercent / 100;
        salary += raise;
    }
    // 关键在此处（任何实现Comparable接口的类都需要包含compareTo方法）
    public int compareTo(Employee other){  // 实现compareTo必须声明public
        return Double.compare(salary, other.salary);  // 使用静态Double.compare方法进行比较
    }
}
