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

import elearning.modules.InstructorCourseDescription;
import elearning.dbutils.InstructorCourseDescriptionDbUtil;

@ManagedBean
@SessionScoped
public class InstructorCourseDescriptionController {
	private List<InstructorCourseDescription> instructorcoursedescription;

	private InstructorCourseDescriptionDbUtil instructorcoursedescriptionDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public InstructorCourseDescriptionController() throws Exception {
		instructorcoursedescription = new ArrayList<>();

		instructorcoursedescriptionDbUtil = InstructorCourseDescriptionDbUtil.getInstance();
	}
	
	public List<InstructorCourseDescription> getInstructorCourses() {
		return instructorcoursedescription;
	}
	//Add
	public String addInstructorCourseDescription(InstructorCourseDescription instructorcoursesdesc) {
		logger.info("Adding instructorcourse: " + instructorcoursesdesc);
		try {
			instructorcoursedescriptionDbUtil.addInstructorCourseDescription(instructorcoursesdesc);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding instructorcoursesdesc", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadInstructorCourseDescriptions() {
		instructorcoursedescription.clear();
		try {
			instructorcoursedescription = instructorcoursedescriptionDbUtil.getInstructorCourseDescription();
			logger.info("loading instructorcoursedescription");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading instructorcoursedescription", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadInstructorCourseDescriptionById(int instructorcoursesdescid) {
		logger.info("loading instructorcoursedescription: " + instructorcoursesdescid);
		try {
			// get student from database
			InstructorCourseDescription instructorcoursedescription = instructorcoursedescriptionDbUtil.getInstructorCourseDescription(instructorcoursesdescid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("instructorCourseDescription", instructorcoursedescription);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateInstructorCourseDescription(InstructorCourseDescription instructorcoursesdesc) {
		logger.info("updating instructorcoursesdesc: " + instructorcoursesdesc);
		try {
			instructorcoursedescriptionDbUtil.updateInstructorCourseDescription(instructorcoursesdesc);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating instructorcoursesdesc: " + instructorcoursesdesc, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteInstructorCourseDescription(int instructorcoursesdescId) {
		logger.info("Deleting instructorcourse id: " + instructorcoursesdescId);
		try {
			instructorcoursedescriptionDbUtil.deleteInstructorCourseDescription(instructorcoursesdescId);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting instructorcoursesdescId id: " + instructorcoursesdescId, exc);
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
