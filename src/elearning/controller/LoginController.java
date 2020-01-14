package elearning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import elearning.dbutils.UserAccountDbUtil;
import elearning.modules.UserAccount;

@ManagedBean
@ApplicationScoped



public class LoginController {
	private UserAccountDbUtil userAccountDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public LoginController() throws Exception{
		userAccountDbUtil = UserAccountDbUtil.getInstance();
	}
	
	public String login(UserAccount setCredential) throws Exception {
		List<UserAccount> loginInfo = new ArrayList<>();
		loginInfo = userAccountDbUtil.login(setCredential);
		
		if(loginInfo.size()==1) {
			setCredential.setIsValid("LOGIN SUCCESS");
			setCredential.setUsertype("STUDENT");
			setCredential.setId(loginInfo.get(0).getId());
		}else {
			setCredential.setIsValid("LOGIN FAILED");
		}

		return "message.xhtml";	
		
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
		
		
		
	
}
