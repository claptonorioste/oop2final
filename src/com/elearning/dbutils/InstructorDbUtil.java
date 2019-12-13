package com.elearning.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.elearning.modules.Instructor;

public class InstructorDbUtil {

	private static InstructorDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorDbUtil();
		}

		return instance;
	}

	private InstructorDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}

	public List<Instructor> getInstructor() throws Exception{
		List<Instructor> instructors = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorinfo order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("instructorid");
				String firstName = myRs.getString("firstname");
				String lastName = myRs.getString("lastname");
				String middleName = myRs.getString("middlename");
				String bday = myRs.getString("bday");
				String contactNo = myRs.getString("contactno");
				String address = myRs.getString("address");
				String email = myRs.getString("emailadd");
				int countryId = myRs.getInt("countryid");
				int useraccId = myRs.getInt("useracctid");

				Instructor tempInstructor = new Instructor( id, lastName, firstName, middleName, bday, contactNo, address, email, countryId, useraccId);
				instructors.add(tempInstructor);
			}
			return instructors;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addInstructor(Instructor instructor) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorinfo values (?,?,?,?,?,?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, instructor.getLastName());
			myStmt.setString(2, instructor.getFirstName());
			myStmt.setString(3, instructor.getMiddleName());
			myStmt.setString(4, instructor.getBday());
			myStmt.setString(5, instructor.getContactNo());
			myStmt.setString(6, instructor.getAddress());
			myStmt.setString(7, instructor.getEmail());
			myStmt.setInt(8, instructor.getCountryId());
			myStmt.setInt(9, instructor.getCountryId());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public Instructor getInstructor(int instructorid) throws Exception {
		Instructor instructor = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorinfo where instructorid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("instructorid");
				String firstName = myRs.getString("firstname");
				String lastName = myRs.getString("lastname");
				String middleName = myRs.getString("middlename");
				String bday = myRs.getString("bday");
				String contactNo = myRs.getString("contactno");
				String address = myRs.getString("address");
				String email = myRs.getString("emailadd");
				int countryId = myRs.getInt("countryid");
				int useraccId = myRs.getInt("useracctid");

				instructor =  new Instructor( id, lastName, firstName, middleName, bday, contactNo, address, email, countryId, useraccId);
			}
			else {
				throw new Exception("Could not find student id: " + instructorid);
			}

			return instructor;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateInstructor(Instructor instructor) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorinfo "
						+ " set lastname=?, firstname=?, middlename=?, bday=?, contactno=?, "
						+ "	address=?, emailadd=?, countryid=?, useracctid=?"
						+ " where instructorid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, instructor.getLastName());
			myStmt.setString(2, instructor.getFirstName());
			myStmt.setString(3, instructor.getMiddleName());
			myStmt.setString(4, instructor.getBday());
			myStmt.setString(5, instructor.getContactNo());
			myStmt.setString(6, instructor.getAddress());
			myStmt.setString(7, instructor.getEmail());
			myStmt.setInt(8, instructor.getCountryId());
			myStmt.setInt(9, instructor.getCountryId());
			myStmt.setInt(10, instructor.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteInstructor(int instructorId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorinfo where instructorid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorId);
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}	

	private Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}

	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {
		try {
			if (theRs != null) {
				theRs.close();
			}
			if (theStmt != null) {
				theStmt.close();
			}
			if (theConn != null) {
				theConn.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}	
}