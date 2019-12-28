package elearning.modules;

public class CourseCategory {
	private int id;
	private String categoryName;
	
	public CourseCategory() {
		super();
	}
	public CourseCategory(int id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "CourseCategory [id=" + id + ", categoryName=" + categoryName + "]";
	}
	
}
