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

import elearning.modules.Modules;
public class ModulesDbUtil {
	private static ModulesDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static ModulesDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ModulesDbUtil();
		}

		return instance;
	}

	private ModulesDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<Modules> getModules() throws Exception{
		List<Modules> modules = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecmodules order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("moduleid");
				String moduleName = myRs.getString("modulename");

				Modules tempModules = new Modules(id,moduleName);
				modules.add(tempModules);
			}
			return modules;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addModules(Modules modules) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblsecmodules values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, modules.getModuleName());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public Modules getModules(int moduleid) throws Exception {
		Modules modules = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecmodules where moduleid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, moduleid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("moduleid");
				String moduleName = myRs.getString("modulename");
				modules = new Modules(id,moduleName);
			}
			else {
				throw new Exception("Could not find moduleid id: " + moduleid);
			}

			return modules;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateModules(Modules modules) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblsecmodules "
						+ " set modulename=? "
						+ " where moduleid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, modules.getModuleName());
			myStmt.setInt(2, modules.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteModules(int moduleid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblsecmodules where moduleid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, moduleid);
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
