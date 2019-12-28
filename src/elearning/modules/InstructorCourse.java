package elearning.modules;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class InstructorCourse {
	private int id;
	private int instructId;
	private int courseId;
	private String dateCreated;
	private String datePublish;
	private double coursePrice;

	public InstructorCourse() {
		super();
	}

	public InstructorCourse(int inscourseId, int instructId, int courseId, String dateCreated,
			String datePublish, double coursePrice) {
		super();
		this.id = inscourseId;
		this.instructId = instructId;
		this.courseId = courseId;
		this.dateCreated = dateCreated;
		this.datePublish = datePublish;
		this.coursePrice = coursePrice;
	}
	public int getInscourseId() {
		return id;
	}
	public void setInscourseId(int inscourseId) {
		this.id = inscourseId;
	}
	public int getInstructId() {
		return instructId;
	}
	public void setInstructId(int instructId) {
		this.instructId = instructId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDatePublish() {
		return datePublish;
	}
	public void setDatePublish(String datePublish) {
		this.datePublish = datePublish;
	}
	public double getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(double coursePrice) {
		this.coursePrice = coursePrice;
	}
	@Override
	public String toString() {
		return "InstructorCourse [inscourseId=" + id + ", instructId=" + instructId + ", courseId=" + courseId
				+ ", dateCreated=" + dateCreated + ", datePublish=" + datePublish + ", coursePrice=" + coursePrice
				+ "]";
	}

}