package com.elearning.controller;

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

import com.elearning.modules.Instructor;
import com.elearning.dbutils.InstructorDbUtil;

@ManagedBean
@SessionScoped
public class InstructorController {

	private List<Instructor> instructor;

	private InstructorDbUtil instructorDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public InstructorController() throws Exception {
		instructor = new ArrayList<>();

		instructorDbUtil = InstructorDbUtil.getInstance();
	}

	public List<Instructor> getInstructors() {
		return instructor;
	}

	public String addInstructor(Instructor instructor) {
		logger.info("Adding instructor: " + instructor);
		try {
			instructorDbUtil.addInstructor(instructor);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding instructor", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}

	public void loadInstructors() {
		instructor.clear();
		try {
			instructor = instructorDbUtil.getInstructor();
			logger.info("loading Instructors");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading instructors", exc);
			addErrorMessage(exc);
		}
	}

	public String loadStudent(int instructorid) {
		logger.info("loading instructor: " + instructorid);
		try {
			// get student from database
			Instructor instructor = instructorDbUtil.getInstructor(instructorid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("instructor", instructor);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	

	public String updateStudent(Instructor instructor) {
		logger.info("updating instructor: " + instructor);
		try {
			instructorDbUtil.updateInstructor(instructor);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating instructor: " + instructor, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}

	public String deleteInstructor(int instructorId) {
		logger.info("Deleting instructor id: " + instructorId);
		try {
			instructorDbUtil.deleteInstructor(instructorId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting instructor id: " + instructorId, exc);
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