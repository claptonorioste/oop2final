package elearning.modules;

public class Modules {
	private int id;
	private String moduleName;
	public Modules() {
		super();
	}
	public Modules(int id, String moduleName) {
		super();
		this.id = id;
		this.moduleName = moduleName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	@Override
	public String toString() {
		return "Modules [id=" + id + ", moduleName=" + moduleName + "]";
	}
	
}
