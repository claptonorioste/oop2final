package elearning.modules;

public class Certification {
	int id;
	String certName;
	public Certification() {
		super();
	}
	public Certification(int id, String certName) {
		super();
		this.id = id;
		this.certName = certName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	@Override
	public String toString() {
		return "Certification [id=" + id + ", certName=" + certName + "]";
	}
	
}
