package inheritance;

public class Manager extends Employee{
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day){
        super(name, salary, year, month, day);  // use the key "super" to excute the class of father
        bonus = 0;
    }

    public double getSalary(){  // Override the function "getSalary" from the super class
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    public void setBon(double b){
        bonus = b;
    }

}
