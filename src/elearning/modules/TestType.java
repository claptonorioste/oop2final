package elearning.modules;

public class TestType {
	int id;
	String testType;
	public TestType() {
		super();
	}
	public TestType(int id, String testType) {
		super();
		this.id = id;
		this.testType = testType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	@Override
	public String toString() {
		return "TestType [id=" + id + ", testType=" + testType + "]";
	}
	
}
