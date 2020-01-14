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

import elearning.modules.CourseCategory;
public class CourseCategoryDbUtil {
	
	private static CourseCategoryDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static CourseCategoryDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new CourseCategoryDbUtil();
		}

		return instance;
	}

	private CourseCategoryDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	
	public List<CourseCategory> getCourseCategory() throws Exception{
		List<CourseCategory> coursecategorys = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcoursecategory";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("coursecatid");
				String categoryName = myRs.getString("categoryname");

				CourseCategory tempCoursecategorys = new CourseCategory( id, categoryName);
				coursecategorys.add(tempCoursecategorys);
			}
			System.out.print(coursecategorys);
			return coursecategorys;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addCourseCategory(CourseCategory coursecategory) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilcoursecategory values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, coursecategory.getCategoryName());
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public CourseCategory getCourseCategory(int instructorcourseid) throws Exception {
		CourseCategory coursecategory = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcoursecategory where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcourseid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("coursecatid");
				String categoryName = myRs.getString("categoryname");
				
				coursecategory =  new CourseCategory( id, categoryName);
			}
			else {
				throw new Exception("Could not find instructor id: " + instructorcourseid);
			}

			return coursecategory;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateCourseCategory(CourseCategory coursecategory) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilcoursecategory "
						+ " set categoryname=? "
						+ " where coursecatid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, coursecategory.getCategoryName());
			myStmt.setInt(2, coursecategory.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteCourseCategory(int id) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilcoursecategory where coursecatid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, id);
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
