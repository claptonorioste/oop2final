package elearning.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import elearning.dbutils.StudentCourseDbUtil;
import elearning.modules.StudentCourse;

public class MycourseController {
	
	private StudentCourseDbUtil studentCourseDbUtil;
	private List<StudentCourse> studentCourse;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public MycourseController() throws Exception {
		studentCourse = new ArrayList<>();
		
		studentCourseDbUtil = StudentCourseDbUtil.getInstance();
	}
	
	public List<StudentCourse> getStudentCourse() {
		return studentCourse;
	}
	
	public void loadCourses() {

		logger.info("Loading student course");
		
		studentCourse.clear();

		try {
			
			// get all student course from database
			studentCourse = studentCourseDbUtil.getStudentCourse();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
