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

import elearning.modules.StudentCourse;
public class StudentCourseDbUtil {
	private static StudentCourseDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static StudentCourseDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new StudentCourseDbUtil();
		}

		return instance;
	}

	private StudentCourseDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<StudentCourse> getStudentCourse() throws Exception{
		List<StudentCourse> studentcart = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblstudentcourse order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("studcourseid");
				int studId = myRs.getInt("studentid");
				int inscourseId = myRs.getInt("inscourseid");
				int payTypeId = myRs.getInt("paytypeid");
				String datePurchased = myRs.getString("datepurchased");
				double paidAmount = myRs.getDouble("paidamount");
				double discount = myRs.getDouble("discount");

				StudentCourse tempStudentCourse = new StudentCourse(id,studId,inscourseId,payTypeId,datePurchased,paidAmount,discount);
				studentcart.add(tempStudentCourse);
			}
			return studentcart;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addStudentCourse(StudentCourse studentcart) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblstudentcourse values (?,?,?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studentcart.getStudId());
			myStmt.setInt(2, studentcart.getInscourseId());
			myStmt.setString(3, studentcart.getDatePurchased());
			myStmt.setInt(4, studentcart.getPayTypeId());
			myStmt.setDouble(5, studentcart.getPaidAmount());
			myStmt.setDouble(6, studentcart.getDiscount());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public StudentCourse getStudentCourse(int studcourseid) throws Exception {
		StudentCourse studentcart = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblstudentcourse where studcourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studcourseid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("studcourseid");
				int studId = myRs.getInt("studentid");
				int inscourseId = myRs.getInt("inscourseid");
				int payTypeId = myRs.getInt("paytypeid");
				String datePurchased = myRs.getString("datepurchased");
				double paidAmount = myRs.getDouble("paidamount");
				double discount = myRs.getDouble("discount");

				studentcart = new StudentCourse(id,studId,inscourseId,payTypeId,datePurchased,paidAmount,discount);
			}
			else {
				throw new Exception("Could not find studcourseid id: " + studcourseid);
			}

			return studentcart;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateStudentCourse(StudentCourse studentcart) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblstudentcourse "
						+ " set studentid=?, inscourseid=?, datepurchased=?, paytypeid=?, paidamount=?, discount=?"
						+ " where studcourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studentcart.getStudId());
			myStmt.setInt(2, studentcart.getInscourseId());
			myStmt.setString(3, studentcart.getDatePurchased());
			myStmt.setInt(4, studentcart.getPayTypeId());
			myStmt.setDouble(5, studentcart.getPaidAmount());
			myStmt.setDouble(6, studentcart.getDiscount());
			myStmt.setInt(7, studentcart.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteStudentCourse(int studcourseid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblstudentcourse where studcourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studcourseid);
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
