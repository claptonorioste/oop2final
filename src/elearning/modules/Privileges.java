package elearning.modules;

public class Privileges {
	int id;
	String privName;
	public Privileges() {
		super();
	}
	public Privileges(int id, String privName) {
		super();
		this.id = id;
		this.privName = privName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrivName() {
		return privName;
	}
	public void setPrivName(String privName) {
		this.privName = privName;
	}
	@Override
	public String toString() {
		return "Privileges [id=" + id + ", privName=" + privName + "]";
	}
	
}
