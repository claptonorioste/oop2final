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

import elearning.modules.Group;
public class GroupDbUtil {
	private static GroupDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static GroupDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new GroupDbUtil();
		}
		return instance;
	}

	private GroupDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Group> getGroup() throws Exception{
		List<Group> group = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecgroup order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("groupid");
				String groupName = myRs.getString("groupname");
				int status = myRs.getInt("status");

				Group tempGroup = new Group( id, groupName, status);
				group.add(tempGroup);
			}
			return group;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addGroup(Group group) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblsecgroup values (?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, group.getGroupName());
			myStmt.setInt(2, group.getStatus());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public Group getGroup(int groupid) throws Exception {
		Group group = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecgroup where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("groupid");
				String groupName = myRs.getString("groupname");
				int status = myRs.getInt("status");

				group =  new Group( id, groupName, status);
			}
			else {
				throw new Exception("Could not find student id: " + groupid);
			}

			return group;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateGroup(Group group) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblsecgroup "
						+ " set status=?, groupname=? "
						+ " where groupid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, group.getStatus());
			myStmt.setString(2, group.getGroupName());
			myStmt.setInt(3, group.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteGroup(int groupId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblsecgroup where groupid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupId);
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
