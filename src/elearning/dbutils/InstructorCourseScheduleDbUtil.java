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

import elearning.modules.InstructorCourseSchedule;
public class InstructorCourseScheduleDbUtil {
	private static InstructorCourseScheduleDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCourseScheduleDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseScheduleDbUtil();
		}

		return instance;
	}

	private InstructorCourseScheduleDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorCourseSchedule> getInstructorCourseSchedule() throws Exception{
		List<InstructorCourseSchedule> instructorcourseschedule = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesched order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscourseschedid");
				String dateFrom = myRs.getString("datefrom");
				String dateTo = myRs.getString("dateto");

				InstructorCourseSchedule tempInstructorCourseSchedule = new InstructorCourseSchedule(id, dateFrom, dateTo);
				instructorcourseschedule.add(tempInstructorCourseSchedule);
			}
			return instructorcourseschedule;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCourseSchedule(InstructorCourseSchedule instructorcourseschedule) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcoursesched values (?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, instructorcourseschedule.getDateFrom());
			myStmt.setString(2, instructorcourseschedule.getDateTo());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCourseSchedule getInstructorCourseSchedule(int instructorcoursescheduleid) throws Exception {
		InstructorCourseSchedule instructorcourseschedule = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcoursesched where inscourseschedid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursescheduleid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscourseschedid");
				String dateFrom = myRs.getString("datefrom");
				String dateTo = myRs.getString("dateto");

				instructorcourseschedule =  new InstructorCourseSchedule(id, dateFrom, dateTo);
			}
			else {
				throw new Exception("Could not find instructorcoursescheduleid id: " + instructorcoursescheduleid);
			}

			return instructorcourseschedule;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCourseSchedule(InstructorCourseSchedule instructorcourseschedule) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcoursesched "
						+ " set datefrom=?, dateto=? "
						+ " where inscourseschedid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, instructorcourseschedule.getDateFrom());
			myStmt.setString(2, instructorcourseschedule.getDateTo());
			myStmt.setInt(3, instructorcourseschedule.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCourseSchedule(int instructorcoursescheduleId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcoursesched where inscourseschedid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursescheduleId);
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
