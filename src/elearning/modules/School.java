package elearning.modules;

public class School {
	private int id;
	private String schoolCode;
	private String schoolName;
	private String schoolAddress;
	
	public School() {
		super();
	}
	public School(int id, String schoolCode, String schoolName, String schoolAddress) {
		super();
		this.id = id;
		this.schoolCode = schoolCode;
		this.schoolName = schoolName;
		this.schoolAddress = schoolAddress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	@Override
	public String toString() {
		return "School [id=" + id + ", schoolCode=" + schoolCode + ", schoolName=" + schoolName + ", schoolAddress="
				+ schoolAddress + "]";
	}
	
}
