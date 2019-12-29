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

import elearning.modules.InstructorCourseSection;
public class InstructorCourseSectionDbUtil {
	private static InstructorCourseSectionDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCourseSectionDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseSectionDbUtil();
		}

		return instance;
	}

	private InstructorCourseSectionDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorCourseSection> getInstructorCourseSection() throws Exception{
		List<InstructorCourseSection> instructorcoursesection = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesection order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscoursesecid");
				int inscourseId = myRs.getInt("inscourseid");
				int sectionNo = myRs.getInt("sectionno");
				String sectionTitle = myRs.getString("sectiontitle");

				InstructorCourseSection tempInstructorCourseSection = new InstructorCourseSection(id,inscourseId,sectionNo,sectionTitle);
				instructorcoursesection.add(tempInstructorCourseSection);
			}
			return instructorcoursesection;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCourseSection(InstructorCourseSection instructorcoursesection) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursesection values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesection.getInscourseId());
			myStmt.setInt(2, instructorcoursesection.getSectionNo());
			myStmt.setString(3, instructorcoursesection.getSectionTitle());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCourseSection getInstructorCourseSection(int instructorcoursesectionid) throws Exception {
		InstructorCourseSection instructorcoursesection = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesection where inscoursesecid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesectionid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscoursesecid");
				int inscourseId = myRs.getInt("inscourseid");
				int sectionNo = myRs.getInt("sectionno");
				String sectionTitle = myRs.getString("sectiontitle");
				
				instructorcoursesection = new InstructorCourseSection(id,inscourseId,sectionNo,sectionTitle);
			}
			else {
				throw new Exception("Could not find instructorcoursesectionid id: " + instructorcoursesectionid);
			}

			return instructorcoursesection;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCourseSection(InstructorCourseSection instructorcoursesection) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcoursesection "
						+ " set inscourseid=?,sectionno=?,sectiontitle=? "
						+ " where inscoursesecid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesection.getInscourseId());
			myStmt.setInt(2, instructorcoursesection.getSectionNo());
			myStmt.setString(3, instructorcoursesection.getSectionTitle());
			myStmt.setInt(4, instructorcoursesection.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCourseSection(int instructorcoursesectionid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcoursesection where inscoursesecid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesectionid);
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
