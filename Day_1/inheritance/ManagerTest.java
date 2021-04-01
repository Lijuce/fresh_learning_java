package inheritance;

//

public class ManagerTest {
    public static void main(String[] args) {
        Manager boss = new Manager("carl", 8000, 1987, 12, 15);
        boss.setBon(5000);

        Employee[] staff = new Employee[3];

        staff[0] = boss;
        staff[1] = new Employee("A", 5000, 1989, 10, 1);
        staff[2] = new Employee("B", 3000, 1990, 3, 15);

        for (Employee e : staff){
            System.out.println("name=" + e.getName() + ", salary=" + e.getSalary());

        }

    }
}
