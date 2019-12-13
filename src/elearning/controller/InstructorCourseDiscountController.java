package elearning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import elearning.dbutils.InstructorCourseDiscountDbUtils;
import elearning.modules.InstructorCourseDiscount;

public class InstructorCourseDiscountController {
	
	
	private List<InstructorCourseDiscount> instructorCourseDiscount;
	
	private InstructorCourseDiscountDbUtils instructorCourseDiscountDbUtils;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public InstructorCourseDiscountController() throws Exception {
		instructorCourseDiscount = new ArrayList<>();
		
		instructorCourseDiscountDbUtils = InstructorCourseDiscountDbUtils.getInstance();
	}
	
	public List<InstructorCourseDiscount> getInstructors() {
		return instructorCourseDiscount;
	}
	
	public String setInstructorDiscountController(InstructorCourseDiscount discount) {
			try {
				instructorCourseDiscountDbUtils.setInstructorDisc(discount);	
			} catch (Exception exc) {
				// send this to server logs
				logger.log(Level.SEVERE, "Error setting discount", exc);
				// add error message for JSF page
				addErrorMessage(exc);

				return null;
			}
			
			return "";
		}
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}


