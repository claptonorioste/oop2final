package elearning.modules;

public class Group {
	private int id;
	private String groupName;
	private int status;
	public Group() {
		super();
	}
	public Group(int id, String groupName, int status) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", groupName=" + groupName + ", status=" + status + "]";
	}
}
