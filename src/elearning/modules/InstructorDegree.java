package elearning.modules;

public class InstructorDegree {
	private int id;
	private int degreeId;
	private String yearGraduated;
	private int schoolId;
	
	public InstructorDegree() {
		super();
	}
	public InstructorDegree(int id, int degreeId, String yearGraduated, int schoolId) {
		super();
		this.id = id;
		this.degreeId = degreeId;
		this.yearGraduated = yearGraduated;
		this.schoolId = schoolId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDegreeId() {
		return degreeId;
	}
	public void setDegreeId(int degreeId) {
		this.degreeId = degreeId;
	}
	public String getYearGraduated() {
		return yearGraduated;
	}
	public void setYearGraduated(String yearGraduated) {
		this.yearGraduated = yearGraduated;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	@Override
	public String toString() {
		return "InstructorDegree [id=" + id + ", degreeId=" + degreeId + ", yearGraduated=" + yearGraduated
				+ ", schoolId=" + schoolId + "]";
	}
	
}
