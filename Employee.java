import java.util.*;

public class Employee {
    public static void main(String[] args) {
        Employee employee1 = new Employee("BURCU", 1500000, 2);
        promote(employee1);
        promote(employee1);
        promote(employee1);
        demote(employee1);
        employee1.doWork();
        System.out.println(employee1.toString());

    }

    private static Employee[] employees = new Employee[8];
    private static int count = 0;
    private int id = -1;
    private String name = "Default Employee";
    private double salary = 23217.53;
    private int level = 0;

    public Employee(String name, double salary, int level) {
        if (count > 8) {
            throw new RuntimeException(" Cannot create more than 8 employees.");
        }
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        this.salary = salary;
        this.level = level;
        this.id = generateId(level);
        employees[count++] = this;

    }

    public int getId() {
        return id;

    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public static void promote(Employee e) {
        e.level++;
    }

    public static void demote(Employee e) {
        e.level--;
    }

    private static int generateId(int level) {
        int seconddigit = (int) (Math.random() * 10);
        int thirddigit = (int) (Math.random() * 10);
        int fourthdigit = (int) (Math.random() * 10);
        String Id2 = "" + level + seconddigit + thirddigit + fourthdigit;
        int ıd3 = Integer.parseInt(Id2);
        return ıd3;
    }

    public void doWork() {
        System.out.println(this.name + " DONE WORK");
    }

    public String toString() {
        return " Name: " + getName() + " id " + getId() + " Salary: " + getSalary() + " Level " + getLevel();
    }
}
