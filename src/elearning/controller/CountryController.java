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

import elearning.modules.Country;
import elearning.dbutils.CountryDbUtil;

@ManagedBean
@SessionScoped
public class CountryController {
	private List<Country> country;

	private CountryDbUtil countrydbutil;

	private Logger logger = Logger.getLogger(getClass().getName());

	public CountryController() throws Exception {
		country = new ArrayList<>();

		countrydbutil = CountryDbUtil.getInstance();
	}
	
	public List<Country> getCountrys() {
		return country;
	}

	public String addCountry(Country country) {
		logger.info("Adding country: " + country);
		try {
			countrydbutil.addCountry(country);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding students", exc);
			addErrorMessage(exc);
			return null;
		}
		return "";
	}

	public void loadCountrys() {
		country.clear();
		try {
			country = countrydbutil.getCountry();
			logger.info("loading country");

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading country", exc);
			addErrorMessage(exc);
		}
	}

	public String loadCountry(int countryid) {
		logger.info("loading country: " + countryid);
		try {
			// get student from database
			Country country = countrydbutil.getCountry(countryid);
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("country", country);	

		} catch (Exception exc) {
			return null;
		}
		return "";
	}	

	public String updateStudent(Country country) {
		logger.info("updating country: " + country);
		try {
			countrydbutil.updateCountry(country);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating country: " + country, exc);
			addErrorMessage(exc);

			return null;
		}
		return "";		
	}

	public String deleteInstructor(int countryId) {
		logger.info("Deleting country id: " + countryId);
		try {
			countrydbutil.deleteCountry(countryId);

		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error deleting country id: " + countryId, exc);
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
