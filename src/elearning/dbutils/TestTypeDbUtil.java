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

import elearning.modules.TestType;
public class TestTypeDbUtil {
	private static TestTypeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static TestTypeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new TestTypeDbUtil();
		}

		return instance;
	}

	private TestTypeDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<TestType> getTestType() throws Exception{
		List<TestType> testtype = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutiltesttype order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("testtypeid");
				String testType = myRs.getString("testtypename");

				TestType tempTestType = new TestType(id,testType);
				testtype.add(tempTestType);
			}
			return testtype;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addTestType(TestType testtype) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutiltesttype values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, testtype.getTestType());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public TestType getTestType(int testtypeid) throws Exception {
		TestType testtype = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutiltesttype where testtypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, testtypeid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("testtypeid");
				String testType = myRs.getString("testtypename");
				
				testtype = new TestType(id,testType);
			}
			else {
				throw new Exception("Could not find testtypeid id: " + testtypeid);
			}

			return testtype;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateTestType(TestType testtype) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutiltesttype "
						+ " set testtypename=? "
						+ " where testtypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, testtype.getTestType());
			myStmt.setInt(2, testtype.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteTestType(int testtypeid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutiltesttype where testtypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, testtypeid);
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
