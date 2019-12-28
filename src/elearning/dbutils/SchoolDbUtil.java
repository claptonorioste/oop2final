package elearning.dbutils;

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

import elearning.modules.School;

public class SchoolDbUtil {
	private static SchoolDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static SchoolDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new SchoolDbUtil();
		}

		return instance;
	}

	private SchoolDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<School> getSchool() throws Exception{
		List<School> school = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilschool order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("schoolid");
				String schoolCode = myRs.getString("schoolcode");
				String schoolName = myRs.getString("schoolname");
				String schoolAddress = myRs.getString("schooladdress");

				School tempSchool = new School(id,schoolCode,schoolName,schoolAddress);
				school.add(tempSchool);
			}
			return school;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addSchool(School school) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilschool values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, school.getSchoolCode());
			myStmt.setString(2, school.getSchoolName());
			myStmt.setString(3, school.getSchoolAddress());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public School getSchool(int schoolid) throws Exception {
		School school = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilschool where paytypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, schoolid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("schoolid");
				String schoolCode = myRs.getString("schoolcode");
				String schoolName = myRs.getString("schoolname");
				String schoolAddress = myRs.getString("schooladdress");

				school = new School(id,schoolCode,schoolName,schoolAddress);
			}
			else {
				throw new Exception("Could not find schoolid id: " + schoolid);
			}

			return school;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateSchool(School school) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilschool "
						+ " set schoolcode=?, schoolname=?, schooladdress=? "
						+ " where schoolid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, school.getSchoolCode());
			myStmt.setString(2, school.getSchoolName());
			myStmt.setString(3, school.getSchoolAddress());
			myStmt.setInt(4, school.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteSchool(int schoolid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilschool where paytypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, schoolid);
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Connection
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
