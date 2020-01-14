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

import elearning.modules.Course;

@ManagedBean
@ApplicationScoped

public class CourseDbUtil {
	private static CourseDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static CourseDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new CourseDbUtil();
		}

		return instance;
	}

	private CourseDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<Course> getCourse() throws Exception{
		List<Course> course = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcourse order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("courseid");
				String courseCode = myRs.getString("coursecode");
				String courseName = myRs.getString("coursename");
				int courseCategId = myRs.getInt("coursecatid");

				Course tempCourse = new Course(id,courseCode,courseName,courseCategId);
				course.add(tempCourse);
			}
			return course;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addCourse(Course course) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilcourse values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, course.getCourseCode());
			myStmt.setString(2, course.getCourseName());
			myStmt.setInt(3, course.getCourseCategId());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public Course getCourse(int courseid) throws Exception {
		Course course = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcourse where courseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, courseid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("courseid");
				String courseCode = myRs.getString("coursecode");
				String courseName = myRs.getString("coursename");
				int courseCategId = myRs.getInt("coursecatid");
				
				course = new Course(id,courseCode,courseName,courseCategId);
			}
			else {
				throw new Exception("Could not find courseid id: " + courseid);
			}

			return course;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateCourse(Course course) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilcourse "
						+ " set coursecode=?,coursename=?,coursecatid=? "
						+ " where courseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, course.getCourseCode());
			myStmt.setString(2, course.getCourseName());
			myStmt.setInt(3, course.getCourseCategId());
			myStmt.setInt(4, course.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteCourse(int courseid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilcourse where courseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, courseid);
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
	public void print() {
		System.out.print("HELLO");
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
