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

import elearning.modules.IntructorCourseSecContent;
public class IntructorCourseSecContentDbUtil {
	private static IntructorCourseSecContentDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static IntructorCourseSecContentDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new IntructorCourseSecContentDbUtil();
		}

		return instance;
	}

	private IntructorCourseSecContentDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<IntructorCourseSecContent> getIntructorCourseSecContent() throws Exception{
		List<IntructorCourseSecContent> intructorcourseseccontent = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesectioncontent order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscoursesecconid");
				String title = myRs.getString("title");
				int contentTypeId = myRs.getInt("contenttypeid");
				String contentLocation = myRs.getString("contentlocation");
				int titleOrderNo = myRs.getInt("titleorderno");

				IntructorCourseSecContent tempIntructorCourseSecContent = new IntructorCourseSecContent(id,title,contentTypeId,contentLocation,titleOrderNo);
				intructorcourseseccontent.add(tempIntructorCourseSecContent);
			}
			return intructorcourseseccontent;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addIntructorCourseSecContent(IntructorCourseSecContent intructorcourseseccontent) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursesectioncontent values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, intructorcourseseccontent.getTitle());
			myStmt.setInt(2, intructorcourseseccontent.getContentTypeId());
			myStmt.setString(3, intructorcourseseccontent.getContentLocation());
			myStmt.setInt(4, intructorcourseseccontent.getTitleOrderNo());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public IntructorCourseSecContent getIntructorCourseSecContent(int intructorcourseseccontentid) throws Exception {
		IntructorCourseSecContent intructorcourseseccontent = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesectioncontent where inscoursesecconid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, intructorcourseseccontentid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscoursesecconid");
				String title = myRs.getString("title");
				int contentTypeId = myRs.getInt("contenttypeid");
				String contentLocation = myRs.getString("contentlocation");
				int titleOrderNo = myRs.getInt("titleorderno");
				
				intructorcourseseccontent = new IntructorCourseSecContent(id,title,contentTypeId,contentLocation,titleOrderNo);
			}
			else {
				throw new Exception("Could not find intructorcourseseccontentid id: " + intructorcourseseccontentid);
			}

			return intructorcourseseccontent;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateIntructorCourseSecContent(IntructorCourseSecContent intructorcourseseccontent) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcoursesectioncontent "
						+ " set title=?,contenttypeid=?,contentlocation=?,titleorderno=? "
						+ " where inscoursesecconid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, intructorcourseseccontent.getTitle());
			myStmt.setInt(2, intructorcourseseccontent.getContentTypeId());
			myStmt.setString(3, intructorcourseseccontent.getContentLocation());
			myStmt.setInt(4, intructorcourseseccontent.getTitleOrderNo());
			myStmt.setInt(5, intructorcourseseccontent.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteIntructorCourseSecContent(int intructorcourseseccontentid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcoursesectioncontent where inscoursesecconid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, intructorcourseseccontentid);
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
