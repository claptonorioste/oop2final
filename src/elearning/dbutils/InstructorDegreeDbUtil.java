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

import elearning.modules.InstructorDegree;
public class InstructorDegreeDbUtil {
	private static InstructorDegreeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorDegreeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorDegreeDbUtil();
		}

		return instance;
	}

	private InstructorDegreeDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorDegree> getInstructorDegree() throws Exception{
		List<InstructorDegree> instructordegree = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructordegree order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("insdegreeid");
				int degreeId = myRs.getInt("degreeid");
				String yearGraduated = myRs.getString("yeargraduated");
				int schoolId = myRs.getInt("schoolid");

				InstructorDegree tempInstructorDegree = new InstructorDegree(id,degreeId,yearGraduated,schoolId);
				instructordegree.add(tempInstructorDegree);
			}
			return instructordegree;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorDegree(InstructorDegree instructordegree) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructordegree values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructordegree.getDegreeId());
			myStmt.setString(2, instructordegree.getYearGraduated());
			myStmt.setInt(3, instructordegree.getSchoolId());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorDegree getInstructorDegree(int instructordegreeid) throws Exception {
		InstructorDegree instructordegree = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructordegree where insdegreeid	=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructordegreeid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("insdegreeid");
				int degreeId = myRs.getInt("degreeid");
				String yearGraduated = myRs.getString("yeargraduated");
				int schoolId = myRs.getInt("schoolid");
				
				instructordegree = new InstructorDegree(id,degreeId,yearGraduated,schoolId);
			}
			else {
				throw new Exception("Could not find instructordegreeid id: " + instructordegreeid);
			}

			return instructordegree;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorDegree(InstructorDegree instructordegree) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructordegree "
						+ " set degreeid=? ,yeargraduated=?,schoolid=?"
						+ " where insdegreeid	=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructordegree.getDegreeId());
			myStmt.setString(2, instructordegree.getYearGraduated());
			myStmt.setInt(3, instructordegree.getSchoolId());
			myStmt.setInt(4, instructordegree.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorDegree(int instructordegreeid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructordegree where insdegreeid	=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructordegreeid);
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
