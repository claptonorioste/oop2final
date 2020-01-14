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

import com.orioste_practical.Student;

import elearning.dbutils.StudentCartDbUtil;
import elearning.modules.StudentCart;
import elearning.modules.UserAccount;

@ManagedBean
@ApplicationScoped

public class CartController {
	
	private StudentCartDbUtil studentCartDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public CartController() throws Exception{
		studentCartDbUtil = StudentCartDbUtil.getInstance();
	}
	
	public String addCart(StudentCart thestudentcart) {

		logger.info("Adding student: " + thestudentcart);

		try {
			
			// add student to the database
			studentCartDbUtil.addStudentCart(thestudentcart);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "message.xhtml";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
