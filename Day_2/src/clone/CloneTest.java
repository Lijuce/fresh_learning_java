package clone;

public class CloneTest {
    public static void main(String[] args) {
        try {  // 由于前面抛出了异常，此处需要进行捕获
            Employee original = new Employee("John", 50000);
            original.setHireDay(2000, 1, 1);
            Employee copy = original.clone();
            copy.raiseSalary(10);
            copy.setHireDay(2010, 2, 2);
            System.out.println("original=" + original);
            System.out.println("copy=" + copy);
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
}
