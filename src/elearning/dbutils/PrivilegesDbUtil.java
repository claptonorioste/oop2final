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

import elearning.modules.Privileges;
public class PrivilegesDbUtil {
	private static PrivilegesDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static PrivilegesDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new PrivilegesDbUtil();
		}

		return instance;
	}

	private PrivilegesDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<Privileges> getPrivileges() throws Exception{
		List<Privileges> privilege = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecprivileges order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("privid");
				String privilegeName = myRs.getString("privilegename");

				Privileges tempPrivileges = new Privileges(id,privilegeName);
				privilege.add(tempPrivileges);
			}
			return privilege;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addPrivileges(Privileges privilege) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblsecprivileges values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, privilege.getPrivName());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public Privileges getPrivileges(int privid) throws Exception {
		Privileges privilege = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecprivileges where privid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, privid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("privid");
				String privilegeName = myRs.getString("privilegename");
				privilege = new Privileges(id,privilegeName);
			}
			else {
				throw new Exception("Could not find privid id: " + privid);
			}

			return privilege;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updatePrivileges(Privileges privilege) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblsecprivileges "
						+ " set certname=? "
						+ " where privid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, privilege.getPrivName());
			myStmt.setInt(2, privilege.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deletePrivileges(int privid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblsecprivileges where privid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, privid);
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
