
class Student {

    private String name;
    private String id;
    private Course[] completedCourses;
    private int completedCount;
    private Course[] enrolledCourses;
    private int enrolledCount;
    private static final int MAX_COURSES = 10;

    public Student(String name, String id) {
        setName(name);
        setId(id);

        this.completedCourses = new Course[MAX_COURSES];
        this.enrolledCourses = new Course[MAX_COURSES];
        this.completedCount = 0;
        this.enrolledCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Course[] getCompletedCourses() {
        return completedCourses;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public Course[] getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown Student";
        } else {
            this.name = name;
        }
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            this.id = "DefaultID";
        } else {
            this.id = id;
        }
    }

    public boolean isEnrolledIn(Course course) {
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledCourses[i] == course) {
                return true;
            }
        }
        return false;
    }

    public boolean isCompleted(Course course) {
        for (int i = 0; i < completedCount; i++) {
            if (completedCourses[i] == course) {
                return true;
            }
        }
        return false;
    }

    public boolean addEnrolledCourse(Course course) {
        if (enrolledCount < MAX_COURSES) {
            enrolledCourses[enrolledCount] = course;
            enrolledCount++;
            return true;
        }
        return false;
    }

    public boolean removeEnrolledCourse(Course course) {
        if (course == null) {
            return false;
        }
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledCourses[i].equals(course)) {
                for (int j = i; j < enrolledCount - 1; j++) {
                    enrolledCourses[j] = enrolledCourses[j + 1];
                }

                enrolledCourses[enrolledCount - 1] = null;
                return true;
            }
        }
        return false;
    }

    public boolean addCompletedCourse(Course course) {
        if (course == null) {
            return false;
        }
        if (completedCount < MAX_COURSES) {
            completedCourses[completedCount] = course;
            completedCount++;
        }
        return true;
    }
}
