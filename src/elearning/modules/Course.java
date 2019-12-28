package elearning.modules;

public class Course {
	int id;
	String courseCode;
	String courseName;
	int courseCategId;
	public Course() {
		super();
	}
	public Course(int id, String courseCode, String courseName, int courseCategId) {
		super();
		this.id = id;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseCategId = courseCategId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseCategId() {
		return courseCategId;
	}
	public void setCourseCategId(int courseCategId) {
		this.courseCategId = courseCategId;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", courseCode=" + courseCode + ", courseName=" + courseName + ", courseCategId="
				+ courseCategId + "]";
	}
	
}
