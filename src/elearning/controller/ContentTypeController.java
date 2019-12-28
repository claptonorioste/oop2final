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

import elearning.dbutils.ContentTypeDbUtil;
import elearning.modules.ContentType;
@ManagedBean
@SessionScoped
public class ContentTypeController {
	private List<ContentType> contenttype;

	private ContentTypeDbUtil contenttypeDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public ContentTypeController() throws Exception {
		contenttype = new ArrayList<>();

		contenttypeDbUtil = ContentTypeDbUtil.getInstance();
	}
	
	public List<ContentType> getInstructorCourses() {
		return contenttype;
	}
	//Add
	public String addContentType(ContentType contenttype) {
		logger.info("Adding contenttype: " + contenttype);
		try {
			contenttypeDbUtil.addContentType(contenttype);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding contenttype", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadContentTypes() {
		contenttype.clear();
		try {
			contenttype = contenttypeDbUtil.getContentType();
			logger.info("loading contenttype");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading contenttype", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadContentTypeById(int contenttypeid) {
		logger.info("loading contenttype: " + contenttypeid);
		try {
			// get student from database
			ContentType contenttype = contenttypeDbUtil.getContentType(contenttypeid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("contentType", contenttype);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateContentType(ContentType contenttype) {
		logger.info("updating contenttype: " + contenttype);
		try {
			contenttypeDbUtil.updateContentType(contenttype);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating contenttype: " + contenttype, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteContentType(int contenttypeId) {
		logger.info("Deleting contenttype id: " + contenttypeId);
		try {
			contenttypeDbUtil.deleteContentType(contenttypeId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting contenttypesdescId id: " + contenttypeId, exc);
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
