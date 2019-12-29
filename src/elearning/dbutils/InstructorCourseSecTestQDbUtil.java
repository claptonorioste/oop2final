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

import elearning.modules.InstructorCourseSecTestQ;
public class InstructorCourseSecTestQDbUtil {
	private static InstructorCourseSecTestQDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCourseSecTestQDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseSecTestQDbUtil();
		}

		return instance;
	}

	private InstructorCourseSecTestQDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorCourseSecTestQ> getInstructorCourseSecTestQ() throws Exception{
		List<InstructorCourseSecTestQ> instructorcoursesectestq = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcorsectestquestion order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscorsectestquestid");
				int inscoursesecTestId = myRs.getInt("inscorsectestid");
				int questionNo = myRs.getInt("questionno");
				String question = myRs.getString("question");
				String answer = myRs.getString("answer");

				InstructorCourseSecTestQ tempInstructorCourseSecTestQ = new InstructorCourseSecTestQ(id,inscoursesecTestId,questionNo,question,answer);
				instructorcoursesectestq.add(tempInstructorCourseSecTestQ);
			}
			return instructorcoursesectestq;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCourseSecTestQ(InstructorCourseSecTestQ instructorcoursesectestq) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcorsectestquestion values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesectestq.getInscoursesecTestId());
			myStmt.setInt(2, instructorcoursesectestq.getQuestionNo());
			myStmt.setString(3, instructorcoursesectestq.getQuestion());
			myStmt.setString(4, instructorcoursesectestq.getAnswer());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCourseSecTestQ getInstructorCourseSecTestQ(int instructorcoursesectestqid) throws Exception {
		InstructorCourseSecTestQ instructorcoursesectestq = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcorsectestquestion where inscorsectestquestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesectestqid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscorsectestquestid");
				int inscoursesecTestId = myRs.getInt("inscorsectestid");
				int questionNo = myRs.getInt("questionno");
				String question = myRs.getString("question");
				String answer = myRs.getString("answer");
				
				instructorcoursesectestq = new InstructorCourseSecTestQ(id,inscoursesecTestId,questionNo,question,answer);
			}
			else {
				throw new Exception("Could not find instructorcoursesectestqid id: " + instructorcoursesectestqid);
			}

			return instructorcoursesectestq;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCourseSecTestQ(InstructorCourseSecTestQ instructorcoursesectestq) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcorsectestquestion "
						+ " set inscorsectestid=?,questionno=?,question=?,answer=? "
						+ " where inscorsectestquestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesectestq.getInscoursesecTestId());
			myStmt.setInt(2, instructorcoursesectestq.getQuestionNo());
			myStmt.setString(3, instructorcoursesectestq.getQuestion());
			myStmt.setString(4, instructorcoursesectestq.getAnswer());
			myStmt.setInt(5, instructorcoursesectestq.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCourseSecTestQ(int instructorcoursesectestqid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcorsectestquestion where inscorsectestquestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursesectestqid);
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
