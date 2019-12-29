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

import elearning.modules.GroupUsers;
public class GroupUsersDbUtil {
	private static GroupUsersDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static GroupUsersDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new GroupUsersDbUtil();
		}

		return instance;
	}

	private GroupUsersDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<GroupUsers> getGroupUsers() throws Exception{
		List<GroupUsers> groupusers = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecgroupusers order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("grpuserid");
				int useraccId = myRs.getInt("useracctid");
				int groupId = myRs.getInt("groupid");
				String dateAdded = myRs.getString("dateadded");

				GroupUsers tempGroupUsers = new GroupUsers(id,useraccId,groupId,dateAdded);
				groupusers.add(tempGroupUsers);
			}
			return groupusers;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addGroupUsers(GroupUsers groupusers) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblsecgroupusers values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupusers.getUseraccId());
			myStmt.setInt(2, groupusers.getGroupId());
			myStmt.setString(3, groupusers.getDateAdded());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public GroupUsers getGroupUsers(int groupusersid) throws Exception {
		GroupUsers groupusers = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecgroupusers where grpuserid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupusersid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("grpuserid");
				int useraccId = myRs.getInt("useracctid");
				int groupId = myRs.getInt("groupid");
				String dateAdded = myRs.getString("dateadded");

				groupusers = new GroupUsers(id,useraccId,groupId,dateAdded);
			}
			else {
				throw new Exception("Could not find groupusersid id: " + groupusersid);
			}

			return groupusers;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateGroupUsers(GroupUsers groupusers) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblsecgroupusers "
						+ " set useracctid=?, groupid=?, dateadded=? "
						+ " where grpuserid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupusers.getUseraccId());
			myStmt.setInt(2, groupusers.getGroupId());
			myStmt.setString(3, groupusers.getDateAdded());
			myStmt.setInt(4, groupusers.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteGroupUsers(int groupusersid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblsecgroupusers where grpuserid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupusersid);
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
