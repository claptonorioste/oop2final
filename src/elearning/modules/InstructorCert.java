package elearning.modules;

public class InstructorCert {
	int id;
	int certId;
	String dateAcquired;
	
	public InstructorCert() {
		super();
	}

	public InstructorCert(int id, int certId, String dateAcquired) {
		super();
		this.id = id;
		this.certId = certId;
		this.dateAcquired = dateAcquired;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCertId() {
		return certId;
	}

	public void setCertId(int certId) {
		this.certId = certId;
	}

	public String getDateAcquired() {
		return dateAcquired;
	}

	public void setDateAcquired(String dateAcquired) {
		this.dateAcquired = dateAcquired;
	}

	@Override
	public String toString() {
		return "InstructorCert [id=" + id + ", certId=" + certId + ", dateAcquired=" + dateAcquired + "]";
	}
	
}
