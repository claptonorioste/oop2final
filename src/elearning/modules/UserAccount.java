package elearning.modules;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class UserAccount {
	private int id;
	private String username;
	private String password;
	private String usertype;
	private String isValid;
	private int status;
	
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public UserAccount() {
		super();
	}
	public UserAccount(int id, String username, String password, int status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + ", status=" + status
				+ "]";
	}
	
}
