
class Teacher {

    private String name;
    private String id;
    private String department;
    private String email;
    private Course[] coursesTaught;
    private int coursesCount;
    private static final int MAX_COURSES = 10;

    public Teacher(String name, String id, String department, String email) {
        setName(name);
        setId(id);
        setDepartment(department);
        setEmail(email);
        this.coursesTaught = new Course[MAX_COURSES];
        this.coursesCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public Course[] getCoursesTaught() {
        return coursesTaught;
    }

    public int getCoursesCount() {
        return coursesCount;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "DefaultName";
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

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            this.department = "DefaultDepartment";
        } else {
            this.department = department;
        }
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            this.email = "default@mail.com";
        } else {
            this.email = email;
        }
    }

    public void assignCourse(Course course) {
        if (course == null) {
            return;
        }
        for (int i = 0; i < coursesCount; i++) {
            if (coursesTaught[i] == course) {
                return;
            }
        }
        if (coursesCount < MAX_COURSES) {
            coursesTaught[coursesCount] = course;
            coursesCount++;
        }
        course.setTeacher(this);
    }

    public void removeCourse(Course course) {
        if (course == null) {
            return;
        }
        int index = -1;
        for (int i = 0; i < coursesCount; i++) {
            if (coursesTaught[i] == course) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < coursesCount - 1; i++) {
                coursesTaught[i] = coursesTaught[i + 1];
            }
            coursesTaught[coursesCount - 1] = null;
            coursesCount--;
        }
    }
}
