package elearning.modules;

public class GroupPriv {
	int id,groupId,privId,moduleId;
	String dateAdded;
	public GroupPriv() {
		super();
	}
	public GroupPriv(int id, int groupId, int privId, int moduleId, String dateAdded) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.privId = privId;
		this.moduleId = moduleId;
		this.dateAdded = dateAdded;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getPrivId() {
		return privId;
	}
	public void setPrivId(int privId) {
		this.privId = privId;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	@Override
	public String toString() {
		return "GroupPriv [id=" + id + ", groupId=" + groupId + ", privId=" + privId + ", moduleId=" + moduleId
				+ ", dateAdded=" + dateAdded + "]";
	}
	
}
