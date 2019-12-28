package elearning.modules;

public class InstructorCourseSchedule {
	private int id;
	private String dateFrom;
	private String dateTo;
	
	public InstructorCourseSchedule() {
		super();
	}
	public InstructorCourseSchedule(int id, String dateFrom, String dateTo) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	@Override
	public String toString() {
		return "InstructorCourseSchedule [id=" + id + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}
	
}
