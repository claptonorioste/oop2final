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

import com.elearning.modules.InstructorCourse;

public class InstructorCourseDbUtil {

	private static InstructorCourseDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCourseDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseDbUtil();
		}

		return instance;
	}

	private InstructorCourseDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}

	public List<InstructorCourse> getInstructorCourse() throws Exception{
		List<InstructorCourse> instructorcourses = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcourse order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscourseid");
				int instructId = myRs.getInt("instructorid");
				int courseId = myRs.getInt("courseid");
				String dateCreated = myRs.getString("datecreated");
				String datePublish = myRs.getString("datepublished");
				Double coursePrice = myRs.getDouble("courseprice");

				InstructorCourse tempInstructorCourse = new InstructorCourse( id, instructId, courseId, dateCreated, datePublish, coursePrice);
				instructorcourses.add(tempInstructorCourse);
			}
			return instructorcourses;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addInstructorCourse(InstructorCourse instructorcourse) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcourse values (?,?,?,?,?,?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcourse.getInstructId());
			myStmt.setInt(2, instructorcourse.getCourseId());
			myStmt.setString(3, instructorcourse.getDateCreated());
			myStmt.setString(4, instructorcourse.getDatePublish());
			myStmt.setDouble(5, instructorcourse.getCoursePrice());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public InstructorCourse getInstructorCourse(int instructorcourseid) throws Exception {
		InstructorCourse instructorcourse = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcourse where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcourseid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscourseid");
				int instructId = myRs.getInt("instructorid");
				int courseId = myRs.getInt("courseid");
				String dateCreated = myRs.getString("datecreated");
				String datePublish = myRs.getString("datepublished");
				Double coursePrice = myRs.getDouble("courseprice");

				instructorcourse =  new InstructorCourse( id, instructId, courseId, dateCreated, datePublish, coursePrice);
			}
			else {
				throw new Exception("Could not find student id: " + instructorcourseid);
			}

			return instructorcourse;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateInstructorCourse(InstructorCourse instructorcourse) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcourse "
						+ " set instructorid=?, courseid=?, datecreated=?, datepublished=?, courseprice=? "
						+ " where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcourse.getInstructId());
			myStmt.setInt(2, instructorcourse.getCourseId());
			myStmt.setString(3, instructorcourse.getDateCreated());
			myStmt.setString(4, instructorcourse.getDatePublish());
			myStmt.setDouble(5, instructorcourse.getCoursePrice());
			myStmt.setInt(6, instructorcourse.getCourseId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteInstructorCourse(int instructorcourseId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcourse where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcourseId);
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