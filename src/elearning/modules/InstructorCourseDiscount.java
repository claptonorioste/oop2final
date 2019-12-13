package elearning.modules;

public class InstructorCourseDiscount {
	InstructorCourseDiscount(){}
	
	private int insCourseDiscID =  0;
	private String dateTo = "";
	private String dateFrom = "" ;
	private int inscourseid =  0;
	public int getInsCourseDiscID() {
		return insCourseDiscID;
	}
	public void setInsCourseDiscID(int insCourseDiscID) {
		this.insCourseDiscID = insCourseDiscID;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public int getInscourseid() {
		return inscourseid;
	}
	public void setInscourseid(int inscourseid) {
		this.inscourseid = inscourseid;
	}
				

}
