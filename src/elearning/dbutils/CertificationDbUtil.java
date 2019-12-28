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

import elearning.modules.Certification;
public class CertificationDbUtil {
	private static CertificationDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static CertificationDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new CertificationDbUtil();
		}

		return instance;
	}

	private CertificationDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<Certification> getCertification() throws Exception{
		List<Certification> certification = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcertification order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("certid");
				String certName = myRs.getString("certname");

				Certification tempCertification = new Certification(id,certName);
				certification.add(tempCertification);
			}
			return certification;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addCertification(Certification certification) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilcertification values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, certification.getCertName());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public Certification getCertification(int certificationid) throws Exception {
		Certification certification = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcertification where certid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, certificationid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("certid");
				String certName = myRs.getString("certname");
				
				certification = new Certification(id,certName);
			}
			else {
				throw new Exception("Could not find certificationid id: " + certificationid);
			}

			return certification;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateCertification(Certification certification) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilcertification "
						+ " set certname=? "
						+ " where certid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, certification.getCertName());
			myStmt.setInt(2, certification.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteCertification(int certificationid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilcertification where certid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, certificationid);
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
