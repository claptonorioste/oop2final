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

import elearning.modules.StudentCart;
public class StudentCartDbUtil {
	private static StudentCartDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static StudentCartDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new StudentCartDbUtil();
		}

		return instance;
	}

	private StudentCartDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<StudentCart> getStudentCart() throws Exception{
		List<StudentCart> studentcart = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblstudentcart order by studcourseid";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("studcartid");
				int studId = myRs.getInt("studentid");
				int inscourseId = myRs.getInt("inscourseid");
				String dateAdded = myRs.getString("dateadded");
				double amountPrice = myRs.getDouble("amountprice");
				double discount = myRs.getDouble("discount");

				StudentCart tempStudentCart = new StudentCart(id,studId,inscourseId,dateAdded,amountPrice,discount);
				studentcart.add(tempStudentCart);
			}
			return studentcart;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addStudentCart(StudentCart studentcart) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "insert into tblstudentcart (studentid, inscourseid, dateadded, amountprice, discount) values (?,?,?,?,?)";
//			String sql = "insert into student (first_name, last_name, email) values (?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studentcart.getStudId());
			myStmt.setInt(2, studentcart.getInscourseId());
			myStmt.setString(3, studentcart.getDateAdded());
			myStmt.setDouble(4, studentcart.getAmountPrice());
			myStmt.setDouble(5, studentcart.getDiscount());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public StudentCart getStudentCart(int studcartid) throws Exception {
		StudentCart studentcart = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblstudentcart where studcartid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studcartid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("studcartid");
				int studId = myRs.getInt("studentid");
				int inscourseId = myRs.getInt("inscourseid");
				String dateAdded = myRs.getString("dateadded");
				double amountPrice = myRs.getDouble("amountprice");
				double discount = myRs.getDouble("discount");

				studentcart = new StudentCart(id,studId,inscourseId,dateAdded,amountPrice,discount);
			}
			else {
				throw new Exception("Could not find studcartid id: " + studcartid);
			}

			return studentcart;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateStudentCart(StudentCart studentcart) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblstudentcart "
						+ " set studentid=?, inscourseid=?, dateadded=?, amountprice=?, discount=? "
						+ " where studcartid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studentcart.getInscourseId());
			myStmt.setString(2, studentcart.getDateAdded());
			myStmt.setDouble(3, studentcart.getAmountPrice());
			myStmt.setDouble(4, studentcart.getDiscount());
			myStmt.setInt(5, studentcart.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteStudentCart(int studcartid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblstudentcart where studcartid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studcartid);
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
