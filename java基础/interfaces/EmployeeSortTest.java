package interfaces;
import java.util.*;

public class EmployeeSortTest {
    public static void main(String[] args) {  
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Harry", 35000);
        staff[1] = new Employee("Carl", 75000);
        staff[2] = new Employee("Tony", 38000);

        Arrays.sort(staff); // Array的sort方法要求对象的类必须实现Comparable接口
    
        for (Employee e: staff){
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary());
        }
    }
}
