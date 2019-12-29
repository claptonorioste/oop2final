package elearning.modules;

public class StudentCart {
	int id,studId,inscourseId;
	String dateAdded;
	double amountPrice,discount;
	public StudentCart() {
		super();
	}
	public StudentCart(int id, int studId, int inscourseId, String dateAdded, double amountPrice, double discount) {
		super();
		this.id = id;
		this.studId = studId;
		this.inscourseId = inscourseId;
		this.dateAdded = dateAdded;
		this.amountPrice = amountPrice;
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
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	public double getAmountPrice() {
		return amountPrice;
	}
	public void setAmountPrice(double amountPrice) {
		this.amountPrice = amountPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "StudentCart [id=" + id + ", studId=" + studId + ", inscourseId=" + inscourseId + ", dateAdded="
				+ dateAdded + ", amountPrice=" + amountPrice + ", discount=" + discount + "]";
	}
	
}
