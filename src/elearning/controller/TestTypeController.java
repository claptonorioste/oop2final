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

import elearning.dbutils.TestTypeDbUtil;
import elearning.modules.TestType;
@ManagedBean
@SessionScoped
public class TestTypeController {
	private List<TestType> testtype;

	private TestTypeDbUtil testtypeDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public TestTypeController() throws Exception {
		testtype = new ArrayList<>();

		testtypeDbUtil = TestTypeDbUtil.getInstance();
	}
	
	public List<TestType> getInstructorCourses() {
		return testtype;
	}
	//Add
	public String addTestType(TestType testtype) {
		logger.info("Adding testtype: " + testtype);
		try {
			testtypeDbUtil.addTestType(testtype);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding testtype", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadTestTypes() {
		testtype.clear();
		try {
			testtype = testtypeDbUtil.getTestType();
			logger.info("loading testtype");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading testtype", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadTestTypeById(int testtypeid) {
		logger.info("loading testtype: " + testtypeid);
		try {
			// get student from database
			TestType testtype = testtypeDbUtil.getTestType(testtypeid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("testType", testtype);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updateTestType(TestType testtype) {
		logger.info("updating testtype: " + testtype);
		try {
			testtypeDbUtil.updateTestType(testtype);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating testtype: " + testtype, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deleteTestType(int testtypeId) {
		logger.info("Deleting testtype id: " + testtypeId);
		try {
			testtypeDbUtil.deleteTestType(testtypeId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting testtypesdescId id: " + testtypeId, exc);
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
