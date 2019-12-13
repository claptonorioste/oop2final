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

import com.elearning.modules.Student;
import com.elearning.dbutils.StudentDbUtil;

@ManagedBean
@SessionScoped
public class StudentController {

private List<Student> student;

	private StudentDbUtil studentDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public StudentController() throws Exception {
		student = new ArrayList<>();

		studentDbUtil = StudentDbUtil.getInstance();
	}

	public List<Student> getStudents() {
		return student;
	}

	public String addStudent(Student student) {
		logger.info("Adding Student: " + student);
		try {
			studentDbUtil.addStudent(student);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding students", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}

	public void loadStudents() {
		student.clear();
		try {
			student = studentDbUtil.getStudent();
			logger.info("loading Students");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading Students", exc);
			addErrorMessage(exc);
		}
	}

	public String loadStudent(int Studentid) {
		logger.info("loading Student: " + Studentid);
		try {
			// get student from database
			Student student = studentDbUtil.getStudent(Studentid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("student", student);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	

	public String updateStudent(Student student) {
		logger.info("updating student: " + student);
		try {
			studentDbUtil.updateStudent(student);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating student: " + student, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}

	public String deleteStudent(int StudentId) {
		logger.info("Deleting Student id: " + StudentId);
		try {
			studentDbUtil.deleteStudent(StudentId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting Student id: " + StudentId, exc);
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