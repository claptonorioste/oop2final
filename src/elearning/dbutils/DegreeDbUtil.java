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

import elearning.modules.Degree;
public class DegreeDbUtil {
	private static DegreeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static DegreeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new DegreeDbUtil();
		}

		return instance;
	}

	private DegreeDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<Degree> getDegree() throws Exception{
		List<Degree> degree = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutildegree order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("degreeid");
				String degreeName = myRs.getString("degreename");

				Degree tempDegree = new Degree(id,degreeName);
				degree.add(tempDegree);
			}
			return degree;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addDegree(Degree degree) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutildegree values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, degree.getDegreeName());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public Degree getDegree(int degreeid) throws Exception {
		Degree degree = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutildegree where degreeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, degreeid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("degreeid");
				String degreeName = myRs.getString("degreename");
				degree = new Degree(id,degreeName);
			}
			else {
				throw new Exception("Could not find degreeid id: " + degreeid);
			}

			return degree;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateDegree(Degree degree) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutildegree "
						+ " set certname=? "
						+ " where degreeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, degree.getDegreeName());
			myStmt.setInt(2, degree.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteDegree(int degreeid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutildegree where degreeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, degreeid);
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
