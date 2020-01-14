package elearning.controller;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.NamedEvent;

import elearning.dbutils.StudentDbUtil;
import elearning.modules.Student;
import elearning.dbutils.InstructorDbUtil;
import elearning.modules.Instructor;
import elearning.dbutils.UserAccountDbUtil;
import elearning.modules.UserAccount;
import elearning.dbutils.CountryDbUtil;
import elearning.dbutils.CourseCategoryDbUtil;
import elearning.modules.Country;


@ManagedBean
@SessionScoped
public class SignupController {
	private Logger logger = Logger.getLogger(getClass().getName());
	private StudentDbUtil studentDbUtil;
	private InstructorDbUtil instructorDbUtil;
	private UserAccountDbUtil useraccountDbUtil;
	private CountryDbUtil countryUtil;
	
	private List<String> countries = Arrays.asList("philippines","philippines","philippines");
	String [] countrys1 = {"philippines","philippines","philippines"};
	List<Country> countryList;
	
	
	public SignupController() throws Exception {
		countryList = new ArrayList<>();
		
		countryUtil = CountryDbUtil.getInstance();
	}
	
	
	
	
	
	public String[] getCountrys1() {
		return countrys1;
	}
	String signUp(Student student,UserAccount useraccount) throws Exception{
		Student newstudent = new Student();
		useraccountDbUtil.addUserAccount(useraccount);
		
		studentDbUtil.addStudent(newstudent);
		return "homepage?faces-redirect=true";
	}
	String signUp(Instructor instructor,UserAccount useraccount) throws Exception {
		instructorDbUtil.addInstructor(instructor);
		return "homepage?faces-redirect=true";
	}
	
	public void loadCountry() {

		logger.info("Loading Country");
		
		countryList.clear();

		try {
			
			// get all students from database
			countryList = countryUtil.getCountry();
			
		} catch (Exception exc) {
			// send this to server logs
			//logger.log(Level.SEVERE, "Error loading students", exc);
			
			// add error message for JSF page
			//addErrorMessage(exc);
		}
	}
	
	public List<Country> getCountries() throws Exception{
		//countrys = countryUtil.getCountrys();
		//logger.info(""+countries);
		return countryList;
	}
	
//	void setCountryslbl() throws Exception{
//		countries = countryUtil.getCountrys();
//		for(int i=0;i<countries.size();i++) {
//			countryslbl.add(String.valueOf(i));
//		}
//		System.out.println(countryslbl);
//	}
	
	
}
