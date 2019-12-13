package elearning.modules;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Instructor {
	private int id;
	private String lastName;
	private String firstName;
	private String middleName;
	private String bday;
	private String contactNo;
	private String address;
	private String email;
	private int countryId;
	private int useraccId;
	
	public Instructor() {
		super();
	}

	public Instructor(int id, String lastName, String firstName, String middleName, String bday, String contactNo,
			String address, String email, int countryId, int useraccId) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.bday = bday;
		this.contactNo = contactNo;
		this.address = address;
		this.email = email;
		this.countryId = countryId;
		this.useraccId = useraccId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getUseraccId() {
		return useraccId;
	}

	public void setUseraccId(int useraccId) {
		this.useraccId = useraccId;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", middleName="
				+ middleName + ", bday=" + bday + ", contactNo=" + contactNo + ", address=" + address + ", email="
				+ email + ", countryId=" + countryId + ", useraccId=" + useraccId + "]";
	}

}
