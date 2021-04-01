package ArrayList;

import inheritance.Employee;
import inheritance.Manager;

import java.util.ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {
        Manager boss = new Manager("carl", 8000, 1987, 12, 15);
        boss.setBon(5000);

        ArrayList<Employee> staff = new ArrayList<> ();
        staff.add(new Employee("A", 5000, 1989, 10, 1));
        staff.add(new Employee("B", 5000, 1989, 10, 1));
        staff.add(new Employee("C", 3000, 1990, 3, 15));


        for (Employee e : staff){
            System.out.println("name=" + e.getName() + ", salary=" + e.getSalary());

        }

    }
}
