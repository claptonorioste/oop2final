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
	//return Lists
	public List<InstructorCourseDiscount> getInstructorCourseDiscount() throws Exception{
		List<InstructorCourseDiscount> instructorcoursediscount = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursediscount order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int insCourseDiscID = myRs.getInt("inscoursedescid");
				String dateTo = myRs.getString("datefrom");
				String dateFrom = myRs.getString("dateto");
				int inscourseid = myRs.getInt("inscourseid");

				InstructorCourseDiscount tempInstructorCourseDiscount = new InstructorCourseDiscount(insCourseDiscID,dateTo,dateFrom,inscourseid);
				instructorcoursediscount.add(tempInstructorCourseDiscount);
			}
			return instructorcoursediscount;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCourseDiscount(InstructorCourseDiscount instructorcoursediscount) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursediscount values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, instructorcoursediscount.getDateFrom());
			myStmt.setString(2, instructorcoursediscount.getDateTo());
			myStmt.setInt(3, instructorcoursediscount.getInscourseid());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCourseDiscount getInstructorCourseDiscount(int instructorcoursediscountid) throws Exception {
		InstructorCourseDiscount instructorcoursediscount = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursediscount where inscoursedescid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursediscountid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int insCourseDiscID = myRs.getInt("inscoursedescid");
				String dateTo = myRs.getString("datefrom");
				String dateFrom = myRs.getString("dateto");
				int inscourseid = myRs.getInt("inscourseid");
				
				instructorcoursediscount = new InstructorCourseDiscount(insCourseDiscID,dateTo,dateFrom,inscourseid);
			}
			else {
				throw new Exception("Could not find tblinstructorcoursediscount id: " + instructorcoursediscountid);
			}

			return instructorcoursediscount;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCourseDiscount(InstructorCourseDiscount instructorcoursediscount) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcoursediscount "
						+ " set datefrom=?,dateto=?,inscourseid=? "
						+ " where inscoursedescid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, instructorcoursediscount.getDateFrom());
			myStmt.setString(2, instructorcoursediscount.getDateTo());
			myStmt.setInt(3, instructorcoursediscount.getInscourseid());
			myStmt.setInt(4, instructorcoursediscount.getInsCourseDiscID());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCourseDiscount(int instructorcoursediscountid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcoursediscount where inscoursedescid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursediscountid);
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
