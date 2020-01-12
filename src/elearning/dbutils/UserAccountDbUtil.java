package elearning.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import elearning.modules.UserAccount;

@ManagedBean
@ApplicationScoped
public class UserAccountDbUtil {
	private static UserAccountDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static UserAccountDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new UserAccountDbUtil();
		}

		return instance;
	}

	private UserAccountDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<UserAccount> getUserAccount() throws Exception{
		List<UserAccount> useraccount = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecuseracct order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("useracctid");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				int status = myRs.getInt("status ");

				UserAccount tempUserAccount = new UserAccount(id,username,password,status);
				useraccount.add(tempUserAccount);
			}
			return useraccount;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addUserAccount(UserAccount useraccount) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblsecuseracct values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, useraccount.getUsername());
			myStmt.setString(2, useraccount.getPassword());
			myStmt.setInt(3, useraccount.getStatus());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public UserAccount getUserAccount(int useraccountid) throws Exception {
		UserAccount useraccount = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecuseracct where useracctid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, useraccountid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("useracctid");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				int status = myRs.getInt("status ");

				useraccount = new UserAccount(id,username,password,status);
			}
			else {
				throw new Exception("Could not find useraccountid id: " + useraccountid);
			}

			return useraccount;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateUserAccount(UserAccount useraccount) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblsecuseracct "
						+ " set username=?, password=?, status=? "
						+ " where useracctid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, useraccount.getUsername());
			myStmt.setString(2, useraccount.getPassword());
			myStmt.setInt(3, useraccount.getStatus());
			myStmt.setInt(4, useraccount.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteUserAccount(int useraccountid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblsecuseracct where useracctid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, useraccountid);
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Send Login Credentials
	public List<UserAccount> login(UserAccount studentCredential) throws Exception{
		List<UserAccount> studentLogin = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecuseracct where username=? AND password = ?";
			myStmt = myConn.prepareStatement(sql);
			
			
			// set params
			
			myStmt.setString(1, studentCredential.getUsername());
			myStmt.setString(2, studentCredential.getPassword());
			myRs = myStmt.executeQuery();
			

			while (myRs.next()) {
				int id = myRs.getInt("useracctid");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				int status = myRs.getInt("status");

				UserAccount loginInfo = new UserAccount(id,username,password,status);
				studentLogin.add(loginInfo);
			}
			return studentLogin;		
		}
		finally {
			close (myConn, myStmt, myRs);
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
