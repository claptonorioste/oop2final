package com.orioste_practical;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean


public class Login {
	private String username;
	private String password;
	private String page;
	
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	
	
	public Login(){}
	
	
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

}
