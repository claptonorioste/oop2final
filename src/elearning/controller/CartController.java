package elearning.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import elearning.dbutils.StudentCartDbUtil;
import elearning.modules.StudentCart;

@ManagedBean
@ApplicationScoped
@SessionScoped

public class CartController {
	
	private StudentCartDbUtil studentCartDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public CartController() throws Exception{
		studentCartDbUtil = StudentCartDbUtil.getInstance();
	}
	
	public String addCart(StudentCart thestudent) {

		logger.info("Adding Cart: " + thestudent);

		try {
			
			// add student to the database
			studentCartDbUtil.addStudentCart(thestudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding to Cart", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "message.xhtml?faces-redirect=true";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
