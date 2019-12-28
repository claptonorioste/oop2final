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

import elearning.modules.InstructorCert;
public class InstructorCertDbUtil {
	private static InstructorCertDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCertDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCertDbUtil();
		}

		return instance;
	}

	private InstructorCertDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorCert> getInstructorCert() throws Exception{
		List<InstructorCert> instructorcert = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcert order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscertid");
				int certId = myRs.getInt("certid");
				String dateAcquired = myRs.getString("dateacquired");

				InstructorCert tempInstructorCert = new InstructorCert(id,certId,dateAcquired);
				instructorcert.add(tempInstructorCert);
			}
			return instructorcert;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCert(InstructorCert instructorcert) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcert values (?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcert.getCertId());
			myStmt.setString(1, instructorcert.getDateAcquired());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCert getInstructorCert(int instructorcertid) throws Exception {
		InstructorCert instructorcert = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcert where inscertid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcertid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscertid");
				int certId = myRs.getInt("certid");
				String dateAcquired = myRs.getString("dateacquired");
				
				instructorcert = new InstructorCert(id,certId,dateAcquired);
			}
			else {
				throw new Exception("Could not find instructorcertid id: " + instructorcertid);
			}

			return instructorcert;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCert(InstructorCert instructorcert) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcert "
						+ " set certid=? ,dateacquired=?"
						+ " where inscertid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcert.getCertId());
			myStmt.setString(2, instructorcert.getDateAcquired());
			myStmt.setInt(3, instructorcert.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCert(int instructorcertid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcert where inscertid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcertid);
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
