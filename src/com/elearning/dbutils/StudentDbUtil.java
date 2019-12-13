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

import com.elearning.modules.Student;

public class StudentDbUtil {
	private static StudentDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static StudentDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new StudentDbUtil();
		}

		return instance;
	}

	private StudentDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}

	public List<Student> getStudent() throws Exception{
		List<Student> Students = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblstudentinfo order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("studentid");
				String firstName = myRs.getString("firstname");
				String lastName = myRs.getString("lastname");
				String middleName = myRs.getString("middlename");
				String bday = myRs.getString("bday");
				String contactNo = myRs.getString("contactno");
				String address = myRs.getString("address");
				String email = myRs.getString("emailadd");
				int countryId = myRs.getInt("countryid");
				int useraccId = myRs.getInt("useracctid");

				Student tempStudent = new Student( id, lastName, firstName, middleName, bday, contactNo, address, email, countryId, useraccId);
				Students.add(tempStudent);
			}
			return Students;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addStudent(Student Student) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblstudentinfo values (?,?,?,?,?,?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, Student.getLastName());
			myStmt.setString(2, Student.getFirstName());
			myStmt.setString(3, Student.getMiddleName());
			myStmt.setString(4, Student.getBday());
			myStmt.setString(5, Student.getContactNo());
			myStmt.setString(6, Student.getAddress());
			myStmt.setString(7, Student.getEmail());
			myStmt.setInt(8, Student.getCountryId());
			myStmt.setInt(9, Student.getCountryId());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public Student getStudent(int Studentid) throws Exception {
		Student Student = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblstudentinfo where studentid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, Studentid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("studentid");
				String firstName = myRs.getString("firstname");
				String lastName = myRs.getString("lastname");
				String middleName = myRs.getString("middlename");
				String bday = myRs.getString("bday");
				String contactNo = myRs.getString("contactno");
				String address = myRs.getString("address");
				String email = myRs.getString("emailadd");
				int countryId = myRs.getInt("countryid");
				int useraccId = myRs.getInt("useracctid");

				Student =  new Student( id, lastName, firstName, middleName, bday, contactNo, address, email, countryId, useraccId);
			}
			else {
				throw new Exception("Could not find student id: " + Studentid);
			}

			return Student;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateStudent(Student Student) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblstudentinfo "
						+ " set lastname=?, firstname=?, middlename=?, bday=?, contactno=?, "
						+ "	address=?, emailadd=?, countryid=?, useracctid=?"
						+ " where studentid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, Student.getLastName());
			myStmt.setString(2, Student.getFirstName());
			myStmt.setString(3, Student.getMiddleName());
			myStmt.setString(4, Student.getBday());
			myStmt.setString(5, Student.getContactNo());
			myStmt.setString(6, Student.getAddress());
			myStmt.setString(7, Student.getEmail());
			myStmt.setInt(8, Student.getCountryId());
			myStmt.setInt(9, Student.getCountryId());
			myStmt.setInt(10, Student.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteStudent(int StudentId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblstudentinfo where studentid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, StudentId);
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
