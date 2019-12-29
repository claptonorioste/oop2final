package elearning.modules;

public class StudentCourse {
	int id,studId,inscourseId,payTypeId;
	String datePurchased;
	double paidAmount,discount;
	
	public StudentCourse() {
		super();
	}
	public StudentCourse(int id, int studId, int inscourseId, int payTypeId, String datePurchased, double paidAmount,
			double discount) {
		super();
		this.id = id;
		this.studId = studId;
		this.inscourseId = inscourseId;
		this.payTypeId = payTypeId;
		this.datePurchased = datePurchased;
		this.paidAmount = paidAmount;
		this.discount = discount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudId() {
		return studId;
	}
	public void setStudId(int studId) {
		this.studId = studId;
	}
	public int getInscourseId() {
		return inscourseId;
	}
	public void setInscourseId(int inscourseId) {
		this.inscourseId = inscourseId;
	}
	public int getPayTypeId() {
		return payTypeId;
	}
	public void setPayTypeId(int payTypeId) {
		this.payTypeId = payTypeId;
	}
	public String getDatePurchased() {
		return datePurchased;
	}
	public void setDatePurchased(String datePurchased) {
		this.datePurchased = datePurchased;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "StudentCourse [id=" + id + ", studId=" + studId + ", inscourseId=" + inscourseId + ", payTypeId="
				+ payTypeId + ", datePurchased=" + datePurchased + ", paidAmount=" + paidAmount + ", discount="
				+ discount + "]";
	}
}
