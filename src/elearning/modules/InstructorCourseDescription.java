package elearning.modules;

public class InstructorCourseDescription {
	private int id;
	private int orderNo;
	private String title;
	private String Description;
	private int courseId;
	
	public InstructorCourseDescription() {
		super();
	}
	public InstructorCourseDescription(int id, int orderNo, String title, String description, int courseId) {
		super();
		this.id = id;
		this.orderNo = orderNo;
		this.title = title;
		this.Description = description;
		this.courseId = courseId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	@Override
	public String toString() {
		return "InstructorCourseDescription [id=" + id + ", orderNo=" + orderNo + ", title=" + title + ", Description="
				+ Description + ", courseId=" + courseId + "]";
	}
	
}
