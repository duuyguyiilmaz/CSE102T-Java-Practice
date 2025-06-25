
class Course {
    private String courseName;
    private String courseCode;
    private Teacher teacher;
    private Course[] prerequisites;
    private int prerequisiteCount;
    private static final int MAX_PREREQUISITES = 5;

    public Course(String courseName, String courseCode, Teacher teacher) {
        setCourseName(courseName);
        setCourseCode(courseCode);
        setTeacher(teacher);
        this.prerequisites = new Course[MAX_PREREQUISITES];
        this.prerequisiteCount = 0;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course[] getPrerequisites() {
        return prerequisites;
    }

    public int getPrerequisiteCount() {
        return prerequisiteCount;
    }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            this.courseName = "Unknown Course";
        } else {
            this.courseName = courseName;
        }
    }

    public void setCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            this.courseCode = "DefaultCode";
        } else {
            this.courseCode = courseCode;
        }
    }

    public void setTeacher(Teacher teacher) {
        if (teacher == null) {
            this.teacher = new Teacher("Default Teacher", "DefaultID", "Default Department", "default@mail.com");
        } else {
            this.teacher = teacher;
        }
    }

    public void addPrerequisite(Course course) {
        if (course == null) {
            return;
        }
        for (int i = 0; i < prerequisiteCount; i++) {
            if (prerequisites[i] == course) {
                return;
            }
        }
        if (prerequisiteCount < MAX_PREREQUISITES) {
            prerequisites[prerequisiteCount] = course;
            prerequisiteCount++;
        }
    }
}
