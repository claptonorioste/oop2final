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

import elearning.modules.InstructorCourseDiscount;

public class InstructorCourseDiscountDbUtils {
	
	private static InstructorCourseDiscountDbUtils instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";
	
	public static InstructorCourseDiscountDbUtils getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseDiscountDbUtils();
		}
		
		return instance;
	}
	
	private InstructorCourseDiscountDbUtils() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	public void setInstructorDisc(InstructorCourseDiscount insCourseDisc ) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursediscount values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, insCourseDisc.getInsCourseDiscID());
			myStmt.setString(2, insCourseDisc.getDateTo());
			myStmt.setString(3, insCourseDisc.getDateFrom());
			myStmt.setInt(4, insCourseDisc.getInscourseid());
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
