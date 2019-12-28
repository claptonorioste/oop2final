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

import elearning.dbutils.InstructorCertDbUtil;
import elearning.modules.InstructorCert;
@ManagedBean
@SessionScoped
public class InstructorCertController {
	private List<InstructorCert> instructorcert;

	private InstructorCertDbUtil instructorcertDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public InstructorCertController() throws Exception {
		instructorcert = new ArrayList<>();

		instructorcertDbUtil = InstructorCertDbUtil.getInstance();
	}
	
	public List<InstructorCert> getInstructorCourses() {
		return instructorcert;
	}
	//Add
	public String addInstructorCert(InstructorCert instructorcert) {
		logger.info("Adding instructorcert: " + instructorcert);
		try {
			instructorcertDbUtil.addInstructorCert(instructorcert);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding instructorcert", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadInstructorCerts() {
		instructorcert.clear();
		try {
			instructorcert = instructorcertDbUtil.getInstructorCert();
			logger.info("loading instructorcert");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading instructorcert", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadInstructorCertById(int instructorcertid) {
		logger.info("loading instructorcert: " + instructorcertid);
		try {
			// get student from database
			InstructorCert instructorcert = instructorcertDbUtil.getInstructorCert(instructorcertid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("instructorCert", instructorcert);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateInstructorCert(InstructorCert instructorcert) {
		logger.info("updating instructorcert: " + instructorcert);
		try {
			instructorcertDbUtil.updateInstructorCert(instructorcert);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating instructorcert: " + instructorcert, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteInstructorCert(int instructorcertId) {
		logger.info("Deleting instructorcert id: " + instructorcertId);
		try {
			instructorcertDbUtil.deleteInstructorCert(instructorcertId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting instructorcertsdescId id: " + instructorcertId, exc);
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
