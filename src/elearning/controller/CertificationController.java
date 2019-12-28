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

import elearning.dbutils.CertificationDbUtil;
import elearning.modules.Certification;
@ManagedBean
@SessionScoped
public class CertificationController {
	private List<Certification> certification;

	private CertificationDbUtil certificationDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public CertificationController() throws Exception {
		certification = new ArrayList<>();

		certificationDbUtil = CertificationDbUtil.getInstance();
	}
	
	public List<Certification> getInstructorCourses() {
		return certification;
	}
	//Add
	public String addCertification(Certification certification) {
		logger.info("Adding certification: " + certification);
		try {
			certificationDbUtil.addCertification(certification);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding certification", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadCertifications() {
		certification.clear();
		try {
			certification = certificationDbUtil.getCertification();
			logger.info("loading certification");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading certification", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadCertificationById(int certificationid) {
		logger.info("loading certification: " + certificationid);
		try {
			// get student from database
			Certification certification = certificationDbUtil.getCertification(certificationid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("certification", certification);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateCertification(Certification certification) {
		logger.info("updating certification: " + certification);
		try {
			certificationDbUtil.updateCertification(certification);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating certification: " + certification, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteCertification(int certificationId) {
		logger.info("Deleting certification id: " + certificationId);
		try {
			certificationDbUtil.deleteCertification(certificationId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting certificationsdescId id: " + certificationId, exc);
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
