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

import elearning.modules.GroupPriv;

public class GroupPrivDbUtil {
	private static GroupPrivDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static GroupPrivDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new GroupPrivDbUtil();
		}
		return instance;
	}

	private GroupPrivDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<GroupPriv> getGroupPriv() throws Exception{
		List<GroupPriv> groupprivs = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecgrouppriv order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("grpprivid");
				int groupId = myRs.getInt("groupid");
				int privId = myRs.getInt("privid");
				int moduleId = myRs.getInt("moduleid");
				String dateAdded = myRs.getString("dateadded");

				GroupPriv tempGroupPriv = new GroupPriv( id, groupId, privId,moduleId,dateAdded);
				groupprivs.add(tempGroupPriv);
			}
			return groupprivs;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addGroupPriv(GroupPriv grouppriv) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblsecgrouppriv values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, grouppriv.getGroupId());
			myStmt.setInt(2, grouppriv.getPrivId());
			myStmt.setInt(3, grouppriv.getModuleId());
			myStmt.setString(4, grouppriv.getDateAdded());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public GroupPriv getGroupPriv(int grpprivid) throws Exception {
		GroupPriv grouppriv = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblsecgrouppriv where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, grpprivid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("grpprivid");
				int groupId = myRs.getInt("groupid");
				int privId = myRs.getInt("privid");
				int moduleId = myRs.getInt("moduleid");
				String dateAdded = myRs.getString("dateadded");

				grouppriv =  new GroupPriv( id, groupId, privId,moduleId,dateAdded);
			}
			else {
				throw new Exception("Could not find student id: " + grpprivid);
			}
			return grouppriv;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateGroupPriv(GroupPriv grouppriv) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblsecgrouppriv "
						+ " set groupid=?, privid=?,moduleid=?,dateadded=? "
						+ " where grpprivid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, grouppriv.getGroupId());
			myStmt.setInt(2, grouppriv.getPrivId());
			myStmt.setInt(3, grouppriv.getModuleId());
			myStmt.setString(4, grouppriv.getDateAdded());
			myStmt.setInt(5, grouppriv.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteGroupPriv(int groupprivId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblsecgrouppriv where grpprivid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, groupprivId);
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
