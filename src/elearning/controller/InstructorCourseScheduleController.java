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

import elearning.dbutils.InstructorCourseScheduleDbUtil;
import elearning.modules.InstructorCourseSchedule;
@ManagedBean
@SessionScoped
public class InstructorCourseScheduleController {
	private List<InstructorCourseSchedule> instructorcourseschedule;

	private InstructorCourseScheduleDbUtil instructorcoursescheduleDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public InstructorCourseScheduleController() throws Exception {
		instructorcourseschedule = new ArrayList<>();

		instructorcoursescheduleDbUtil = InstructorCourseScheduleDbUtil.getInstance();
	}
	
	public List<InstructorCourseSchedule> getInstructorCourses() {
		return instructorcourseschedule;
	}
	//Add
	public String addInstructorCourseSchedule(InstructorCourseSchedule instructorcourseschedule) {
		logger.info("Adding instructorcourse: " + instructorcourseschedule);
		try {
			instructorcoursescheduleDbUtil.addInstructorCourseSchedule(instructorcourseschedule);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding instructorcourseschedule", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadInstructorCourseSchedules() {
		instructorcourseschedule.clear();
		try {
			instructorcourseschedule = instructorcoursescheduleDbUtil.getInstructorCourseSchedule();
			logger.info("loading instructorcourseschedule");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading instructorcourseschedule", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadInstructorCourseScheduleById(int instructorcoursescheduleid) {
		logger.info("loading instructorcourseschedule: " + instructorcoursescheduleid);
		try {
			// get student from database
			InstructorCourseSchedule instructorcourseschedule = instructorcoursescheduleDbUtil.getInstructorCourseSchedule(instructorcoursescheduleid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("instructorCourseSchedule", instructorcourseschedule);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateInstructorCourseSchedule(InstructorCourseSchedule instructorcourseschedule) {
		logger.info("updating instructorcourseschedule: " + instructorcourseschedule);
		try {
			instructorcoursescheduleDbUtil.updateInstructorCourseSchedule(instructorcourseschedule);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating instructorcourseschedule: " + instructorcourseschedule, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteInstructorCourseSchedule(int instructorcoursescheduleId) {
		logger.info("Deleting instructorcourse id: " + instructorcoursescheduleId);
		try {
			instructorcoursescheduleDbUtil.deleteInstructorCourseSchedule(instructorcoursescheduleId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting instructorcoursesdescId id: " + instructorcoursescheduleId, exc);
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
