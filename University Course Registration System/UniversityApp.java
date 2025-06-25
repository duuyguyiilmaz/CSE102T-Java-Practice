public class UniversityApp {
    public static void main(String[] args) {

        Teacher t1 = new Teacher("Dr. Ayşe Yılmaz", "T001", "Computer Science", "ayse.yilmaz@uni.edu");

        Course cs101 = new Course("Introduction to Programming", "CS101", t1);
        Course cs102 = new Course("Data Structures", "CS102", t1);
        Course cs201 = new Course("Algorithms", "CS201", t1);

        cs102.addPrerequisite(cs101);

        Student s1 = new Student("Burcu Yılmaz", "S123");

        s1.addCompletedCourse(cs101);
        s1.addCompletedCourse(cs102);

        boolean canEnroll = true;
        for (int i = 0; i < cs201.getPrerequisiteCount(); i++) {
            Course pre = cs201.getPrerequisites()[i];
            if (!s1.isCompleted(pre)) {
                canEnroll = false;
                break;
            }
        }

        if (canEnroll) {
            s1.addEnrolledCourse(cs201);
            System.out.println(s1.getName() + " has enrolled in " + cs201.getCourseName());
        } else {
            System.out.println(s1.getName() + " does not meet the prerequisites for " + cs201.getCourseName());
        }

        t1.assignCourse(cs101);
        t1.assignCourse(cs102);
        t1.assignCourse(cs201);

        System.out.println("Teacher " + t1.getName() + " teaches:");
        for (int i = 0; i < t1.getCoursesCount(); i++) {
            System.out.println("- " + t1.getCoursesTaught()[i].getCourseName());
        }
    }
}