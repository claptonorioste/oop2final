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

import elearning.dbutils.SchoolDbUtil;
import elearning.modules.School;
@ManagedBean
@SessionScoped
public class SchoolController {
	private List<School> school;

	private SchoolDbUtil schoolDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public SchoolController() throws Exception {
		school = new ArrayList<>();

		schoolDbUtil = SchoolDbUtil.getInstance();
	}
	
	public List<School> getInstructorCourses() {
		return school;
	}
	//Add
	public String addSchool(School school) {
		logger.info("Adding school: " + school);
		try {
			schoolDbUtil.addSchool(school);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding school", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadSchools() {
		school.clear();
		try {
			school = schoolDbUtil.getSchool();
			logger.info("loading school");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading school", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadSchoolById(int schoolid) {
		logger.info("loading school: " + schoolid);
		try {
			// get student from database
			School school = schoolDbUtil.getSchool(schoolid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("school", school);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateSchool(School school) {
		logger.info("updating school: " + school);
		try {
			schoolDbUtil.updateSchool(school);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating school: " + school, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteSchool(int schoolId) {
		logger.info("Deleting school id: " + schoolId);
		try {
			schoolDbUtil.deleteSchool(schoolId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting schoolsdescId id: " + schoolId, exc);
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
