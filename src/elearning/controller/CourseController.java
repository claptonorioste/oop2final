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

import elearning.dbutils.CourseDbUtil;
import elearning.modules.Course;
@ManagedBean
@SessionScoped
public class CourseController {
	private List<Course> course;

	private CourseDbUtil courseDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public CourseController() throws Exception {
		course = new ArrayList<>();

		courseDbUtil = CourseDbUtil.getInstance();
	}
	
	public List<Course> getInstructorCourses() {
		return course;
	}
	//Add
	public String addCourse(Course course) {
		logger.info("Adding course: " + course);
		try {
			courseDbUtil.addCourse(course);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding course", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadCourses() {
		course.clear();
		try {
			course = courseDbUtil.getCourse();
			logger.info("loading course");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading course", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadCourseById(int courseid) {
		logger.info("loading course: " + courseid);
		try {
			// get student from database
			Course course = courseDbUtil.getCourse(courseid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("course", course);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateCourse(Course course) {
		logger.info("updating course: " + course);
		try {
			courseDbUtil.updateCourse(course);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating course: " + course, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteCourse(int courseId) {
		logger.info("Deleting course id: " + courseId);
		try {
			courseDbUtil.deleteCourse(courseId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting coursesdescId id: " + courseId, exc);
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
