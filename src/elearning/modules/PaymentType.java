package elearning.modules;

public class PaymentType {
	private int id;
	private String typeCode;
	private String typeName;
	
	public PaymentType() {
		super();
	}
	public PaymentType(int id, String typeCode, String typeName) {
		super();
		this.id = id;
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Override
	public String toString() {
		return "PaymentType [id=" + id + ", typeCode=" + typeCode + ", typeName=" + typeName + "]";
	}
	
}
