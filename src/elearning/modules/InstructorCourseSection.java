package elearning.modules;

public class InstructorCourseSection {
	private int id;
	private int inscourseId;
	private int sectionNo;
	private String sectionTitle;
		
	public InstructorCourseSection() {
		super();
	}
	public InstructorCourseSection(int id, int inscourseId, int sectionNo, String sectionTitle) {
		super();
		this.id = id;
		this.inscourseId = inscourseId;
		this.sectionNo = sectionNo;
		this.sectionTitle = sectionTitle;
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
	public int getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(int sectionNo) {
		this.sectionNo = sectionNo;
	}
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	@Override
	public String toString() {
		return "InstructorCourseSection [id=" + id + ", inscourseId=" + inscourseId + ", sectionNo=" + sectionNo
				+ ", sectionTitle=" + sectionTitle + "]";
	}
	
}
