package elearning.modules;

public class IntructorCourseTest {
	private int id;
	private int inscourseId;
	private int testTypeId;
	private String deadline;
	
	public IntructorCourseTest() {
		super();
	}
	public IntructorCourseTest(int id, int inscourseId, int testTypeId, String deadline) {
		super();
		this.id = id;
		this.inscourseId = inscourseId;
		this.testTypeId = testTypeId;
		this.deadline = deadline;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInscourseId() {
		return inscourseId;
	}
	public void setInscourseId(int inscourseId) {
		this.inscourseId = inscourseId;
	}
	public int getTestTypeId() {
		return testTypeId;
	}
	public void setTestTypeId(int testTypeId) {
		this.testTypeId = testTypeId;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	@Override
	public String toString() {
		return "IntructorCourseTest [id=" + id + ", inscourseId=" + inscourseId + ", testTypeId=" + testTypeId
				+ ", deadline=" + deadline + "]";
	}
	
	
}
