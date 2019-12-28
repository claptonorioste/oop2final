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

import elearning.modules.InstructorCourse;
import elearning.dbutils.InstructorCourseDbUtil;

@ManagedBean
@SessionScoped
public class InstructorCourseController {
	private List<InstructorCourse> instructorcourse;

	private InstructorCourseDbUtil instructorcourseDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public InstructorCourseController() throws Exception {
		instructorcourse = new ArrayList<>();

		instructorcourseDbUtil = InstructorCourseDbUtil.getInstance();
	}

	public List<InstructorCourse> getInstructorCourses() {
		return instructorcourse;
	}

	public String addInstructorCourse(InstructorCourse instructorcourse) {
		logger.info("Adding instructorcourse: " + instructorcourse);
		try {
			instructorcourseDbUtil.addInstructorCourse(instructorcourse);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding instructorcourse", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}

	public void loadInstructorCourses() {
		instructorcourse.clear();
		try {
			instructorcourse = instructorcourseDbUtil.getInstructorCourse();
			logger.info("loading instructorcourse");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading instructorcourse", exc);
			addErrorMessage(exc);
		}
	}

	public String loadInstructorCourset(int instructorcourseid) {
		logger.info("loading instructorcourse: " + instructorcourseid);
		try {
			// get student from database
			InstructorCourse instructorcourse = instructorcourseDbUtil.getInstructorCourse(instructorcourseid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("instructorCourse", instructorcourse);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	

	public String updateInstructorCourse(InstructorCourse instructorcourse) {
		logger.info("updating instructorcourse: " + instructorcourse);
		try {
			instructorcourseDbUtil.updateInstructorCourse(instructorcourse);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating instructorcourse: " + instructorcourse, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}

	public String deleteInstructorCourse(int instructorcourseId) {
		logger.info("Deleting instructorcourse id: " + instructorcourseId);
		try {
			instructorcourseDbUtil.deleteInstructorCourse(instructorcourseId);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting instructorcourse id: " + instructorcourseId, exc);
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