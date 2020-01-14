package elearning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.orioste_practical.Student;
import com.orioste_practical.StudentDbUtil;

import elearning.dbutils.CourseCategoryDbUtil;
import elearning.modules.CourseCategory;

@ManagedBean
@ApplicationScoped

public class CourseCategoryContrller {
private List<CourseCategory> categoryList;
	
	private CourseCategoryDbUtil courseCategoryDbUtil;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public CourseCategoryContrller() throws Exception {
		categoryList = new ArrayList<>();
		
		courseCategoryDbUtil = CourseCategoryDbUtil.getInstance();
	}
	
	public List<CourseCategory> getCategory() {
		return categoryList;
	}

	public void loadCategory() {

		logger.info("Loading Category");
		
		categoryList.clear();

		try {
			
			// get all students from database
			categoryList = courseCategoryDbUtil.getCourseCategory();
			
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
