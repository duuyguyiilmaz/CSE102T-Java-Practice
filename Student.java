public class Student {
    private String name;
    private double grade;
    private String courses[];
    private int count;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
        this.courses = new String[100];
        this.count = 0;

    }

    public void addCourse(String name) {
        for (int i = 0; i < count; i++) {
            if (courses[i].equals(name)) {
                return;
            }
        }
        if (count < courses.length) {
            courses[count] = name;
            count++;
        }
    }

    public void removeCourse(String name) {
        int index = -1;
        for (int i = 0; i < courses.length; i++) {
            if (courses[i].equals(name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < courses.length - 1; i++) {
                courses[i] = courses[i + 1];
            }
            count--;
            courses[count] = null;
        }
    }

    public void printCourses() {
        for (int i = 0; i < count; i++) {
            System.out.println(courses[i] + ",");
        }
        System.out.println();
    }
}