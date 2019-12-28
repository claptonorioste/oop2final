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

import elearning.dbutils.PaymentTypeDbUtil;
import elearning.modules.PaymentType;
@ManagedBean
@SessionScoped
public class PaymentTypeController {
	private List<PaymentType> paymenttype;

	private PaymentTypeDbUtil paymenttypeDbUtil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public PaymentTypeController() throws Exception {
		paymenttype = new ArrayList<>();

		paymenttypeDbUtil = PaymentTypeDbUtil.getInstance();
	}
	
	public List<PaymentType> getInstructorCourses() {
		return paymenttype;
	}
	//Add
	public String addPaymentType(PaymentType paymenttype) {
		logger.info("Adding paymenttype: " + paymenttype);
		try {
			paymenttypeDbUtil.addPaymentType(paymenttype);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding paymenttype", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}
	//Load
	public void loadPaymentTypes() {
		paymenttype.clear();
		try {
			paymenttype = paymenttypeDbUtil.getPaymentType();
			logger.info("loading paymenttype");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading paymenttype", exc);
			addErrorMessage(exc);
		}
	}
	//Load By Id
	public String loadPaymentTypeById(int paymenttypeid) {
		logger.info("loading paymenttype: " + paymenttypeid);
		try {
			// get student from database
			PaymentType paymenttype = paymenttypeDbUtil.getPaymentType(paymenttypeid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("paymentType", paymenttype);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	
	//Update
	public String updatePaymentType(PaymentType paymenttype) {
		logger.info("updating paymenttype: " + paymenttype);
		try {
			paymenttypeDbUtil.updatePaymentType(paymenttype);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating paymenttype: " + paymenttype, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}
	//Delete
	public String deletePaymentType(int paymenttypeId) {
		logger.info("Deleting paymenttype id: " + paymenttypeId);
		try {
			paymenttypeDbUtil.deletePaymentType(paymenttypeId);;

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting paymenttypesdescId id: " + paymenttypeId, exc);
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
