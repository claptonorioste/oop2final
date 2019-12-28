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

import elearning.modules.InstructorCourseDescription;
public class InstructorCourseDescriptionDbUtil {
	
	private static InstructorCourseDescriptionDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCourseDescriptionDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseDescriptionDbUtil();
		}

		return instance;
	}

	private InstructorCourseDescriptionDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorCourseDescription> getInstructorCourseDescription() throws Exception{
		List<InstructorCourseDescription> instructorcoursesdescsdesc = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesdescdesc order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscoursedescid");
				int orderNo = myRs.getInt("orderno");
				String title = myRs.getString("title");
				String Description = myRs.getString("description");
				int courseId = myRs.getInt("inscourseid");

				InstructorCourseDescription tempInstructorCourseDescription = new InstructorCourseDescription( id, orderNo, title, Description, courseId);
				instructorcoursesdescsdesc.add(tempInstructorCourseDescription);
			}
			return instructorcoursesdescsdesc;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCourseDescription(InstructorCourseDescription instructorcoursesdesc) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursesdesc values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesdesc.getOrderNo());
			myStmt.setString(2, instructorcoursesdesc.getTitle());
			myStmt.setString(3, instructorcoursesdesc.getDescription());
			myStmt.setInt(4, instructorcoursesdesc.getCourseId());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCourseDescription getInstructorCourseDescription(int instructorcoursesdescid) throws Exception {
		InstructorCourseDescription instructorcoursesdesc = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesdesc where inscoursedescid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesdescid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscoursedescid");
				int orderNo = myRs.getInt("orderno");
				String title = myRs.getString("title");
				String Description = myRs.getString("description");
				int courseId = myRs.getInt("inscourseid");

				instructorcoursesdesc =  new InstructorCourseDescription(id, orderNo, title, Description, courseId);
			}
			else {
				throw new Exception("Could not find student id: " + instructorcoursesdescid);
			}

			return instructorcoursesdesc;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCourseDescription(InstructorCourseDescription instructorcoursesdesc) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcoursesdesc "
						+ " set orderno=?, title=?, description=?, inscourseid=? "
						+ " where inscoursedescid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesdesc.getOrderNo());
			myStmt.setString(2, instructorcoursesdesc.getTitle());
			myStmt.setString(3, instructorcoursesdesc.getDescription());
			myStmt.setInt(4, instructorcoursesdesc.getCourseId());
			myStmt.setInt(5, instructorcoursesdesc.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCourseDescription(int instructorcoursesdescId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcoursesdesc where inscoursedescid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesdescId);
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
