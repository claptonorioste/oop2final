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

import elearning.dbutils.DegreeDbUtil;
import elearning.modules.Degree;
@ManagedBean
@SessionScoped
public class DegreeController {
	private List<Degree> degree;

	private DegreeDbUtil degreeDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public DegreeController() throws Exception {
		degree = new ArrayList<>();

		degreeDbUtil = DegreeDbUtil.getInstance();
	}
	
	public List<Degree> getInstructorCourses() {
		return degree;
	}
	//Add
	public String addDegree(Degree degree) {
		logger.info("Adding degree: " + degree);
		try {
			degreeDbUtil.addDegree(degree);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding degree", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadDegrees() {
		degree.clear();
		try {
			degree = degreeDbUtil.getDegree();
			logger.info("loading degree");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading degree", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadDegreeById(int degreeid) {
		logger.info("loading degree: " + degreeid);
		try {
			// get student from database
			Degree degree = degreeDbUtil.getDegree(degreeid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("degree", degree);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateDegree(Degree degree) {
		logger.info("updating degree: " + degree);
		try {
			degreeDbUtil.updateDegree(degree);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating degree: " + degree, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteDegree(int degreeId) {
		logger.info("Deleting degree id: " + degreeId);
		try {
			degreeDbUtil.deleteDegree(degreeId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting degreesdescId id: " + degreeId, exc);
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
