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

import elearning.modules.IntructorCourseTest;
public class IntructorCourseTestDbUtil {
	private static IntructorCourseTestDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static IntructorCourseTestDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new IntructorCourseTestDbUtil();
		}

		return instance;
	}

	private IntructorCourseTestDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<IntructorCourseTest> getIntructorCourseTest() throws Exception{
		List<IntructorCourseTest> intructorcoursetest = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursetest order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscoursetestid");
				int inscourseId = myRs.getInt("inscourseid");
				int testTypeId = myRs.getInt("testtypeid");
				String deadline = myRs.getString("deadline");

				IntructorCourseTest tempIntructorCourseTest = new IntructorCourseTest(id,inscourseId,testTypeId,deadline);
				intructorcoursetest.add(tempIntructorCourseTest);
			}
			return intructorcoursetest;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addIntructorCourseTest(IntructorCourseTest intructorcoursetest) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursetest values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, intructorcoursetest.getInscourseId());
			myStmt.setInt(2, intructorcoursetest.getTestTypeId());
			myStmt.setString(3, intructorcoursetest.getDeadline());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public IntructorCourseTest getIntructorCourseTest(int intructorcoursetestid) throws Exception {
		IntructorCourseTest intructorcoursetest = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursetest where inscoursetestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, intructorcoursetestid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscoursetestid");
				int inscourseId = myRs.getInt("inscourseid");
				int testTypeId = myRs.getInt("testtypeid");
				String deadline = myRs.getString("deadline");
				
				intructorcoursetest = new IntructorCourseTest(id,inscourseId,testTypeId,deadline);
			}
			else {
				throw new Exception("Could not find intructorcoursetestid id: " + intructorcoursetestid);
			}

			return intructorcoursetest;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateIntructorCourseTest(IntructorCourseTest intructorcoursetest) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcoursetest "
						+ " set inscourseid=?,testtypeid=?,deadline=? "
						+ " where inscoursetestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, intructorcoursetest.getInscourseId());
			myStmt.setInt(2, intructorcoursetest.getTestTypeId());
			myStmt.setString(3, intructorcoursetest.getDeadline());
			myStmt.setInt(4, intructorcoursetest.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteIntructorCourseTest(int intructorcoursetestid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcoursetest where inscoursetestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, intructorcoursetestid);
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
