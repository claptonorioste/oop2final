package elearning.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import elearning.dbutils.UserAccountDbUtil;
import elearning.modules.UserAccount;
@ManagedBean
@SessionScoped
public class UserAccountController {
	private List<UserAccount> useraccount;

	private UserAccountDbUtil useraccountDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public UserAccountController() throws Exception {
		useraccount = new ArrayList<>();

		useraccountDbUtil = UserAccountDbUtil.getInstance();
	}
	
	public List<UserAccount> getInstructorCourses() {
		return useraccount;
	}
	//Add
	public String addUserAccount(UserAccount useraccount) {
		logger.info("Adding useraccount: " + useraccount);
		try {
			useraccountDbUtil.addUserAccount(useraccount);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding useraccount", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadUserAccounts() {
		useraccount.clear();
		try {
			useraccount = useraccountDbUtil.getUserAccount();
			logger.info("loading useraccount");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading useraccount", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadUserAccountById(int useraccountid) {
		logger.info("loading useraccount: " + useraccountid);
		try {
			// get student from database
			UserAccount useraccount = useraccountDbUtil.getUserAccount(useraccountid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("userAccount", useraccount);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateUserAccount(UserAccount useraccount) {
		logger.info("updating useraccount: " + useraccount);
		try {
			useraccountDbUtil.updateUserAccount(useraccount);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating useraccount: " + useraccount, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteUserAccount(int useraccountId) {
		logger.info("Deleting useraccount id: " + useraccountId);
		try {
			useraccountDbUtil.deleteUserAccount(useraccountId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting useraccountsdescId id: " + useraccountId, exc);
			addErrorMessage(exc);
			return null;
		}
		return "";	
	}
	//Error Message
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
