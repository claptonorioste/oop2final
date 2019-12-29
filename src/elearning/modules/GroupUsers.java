package elearning.modules;

public class GroupUsers {
	private int id,useraccId,groupId;
	private String dateAdded;
	
	public GroupUsers() {
		super();
	}
	public GroupUsers(int id, int useraccId, int groupId, String dateAdded) {
		super();
		this.id = id;
		this.useraccId = useraccId;
		this.groupId = groupId;
		this.dateAdded = dateAdded;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUseraccId() {
		return useraccId;
	}
	public void setUseraccId(int useraccId) {
		this.useraccId = useraccId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	@Override
	public String toString() {
		return "GroupUsers [id=" + id + ", useraccId=" + useraccId + ", groupId=" + groupId + ", dateAdded=" + dateAdded
				+ "]";
	}
}
