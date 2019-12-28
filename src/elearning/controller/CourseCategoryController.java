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

import elearning.modules.CourseCategory;
import elearning.dbutils.CourseCategoryDbUtil;

@ManagedBean
@SessionScoped
public class CourseCategoryController {
	private List<CourseCategory> coursecategory;

	private CourseCategoryDbUtil coursecategoryDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public CourseCategoryController() throws Exception {
		coursecategory = new ArrayList<>();

		coursecategoryDbUtil = CourseCategoryDbUtil.getInstance();
	}

	public List<CourseCategory> getCourseCategorys() {
		return coursecategory;
	}
	
	public String addCourseCategory(CourseCategory coursecategory) {
		logger.info("Adding coursecategory: " + coursecategory);
		try {
			coursecategoryDbUtil.addCourseCategory(coursecategory);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding coursecategory", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}

	public void loadCourseCategorys() {
		coursecategory.clear();
		try {
			coursecategory = coursecategoryDbUtil.getCourseCategory();
			logger.info("loading coursecategory");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading coursecategory", exc);
			addErrorMessage(exc);
		}
	}

	public String loadCourseCategoryt(int coursecategoryid) {
		logger.info("loading coursecategory: " + coursecategoryid);
		try {
			// get student from database
			CourseCategory coursecategory = coursecategoryDbUtil.getCourseCategory(coursecategoryid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("instructorCourse", coursecategory);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	

	public String updateCourseCategory(CourseCategory coursecategory) {
		logger.info("updating coursecategory: " + coursecategory);
		try {
			coursecategoryDbUtil.updateCourseCategory(coursecategory);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating coursecategory: " + coursecategory, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}

	public String deleteCourseCategory(int coursecategoryId) {
		logger.info("Deleting coursecategory id: " + coursecategoryId);
		try {
			coursecategoryDbUtil.deleteCourseCategory(coursecategoryId);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting coursecategory id: " + coursecategoryId, exc);
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
