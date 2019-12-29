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

import elearning.modules.InstructorCourseTestQ;
public class InstructorCourseTestQDbUtil {
	private static InstructorCourseTestQDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static InstructorCourseTestQDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InstructorCourseTestQDbUtil();
		}

		return instance;
	}

	private InstructorCourseTestQDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<InstructorCourseTestQ> getInstructorCourseTestQ() throws Exception{
		List<InstructorCourseTestQ> instructorcoursetestq = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcortestquestion order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("inscortestquestid");
				int inscourseTestId = myRs.getInt("inscoursetestid");
				int questionNo = myRs.getInt("questionno");
				String question = myRs.getString("question");
				String answer = myRs.getString("answer");

				InstructorCourseTestQ tempInstructorCourseTestQ = new InstructorCourseTestQ(id,inscourseTestId,questionNo,question,answer);
				instructorcoursetestq.add(tempInstructorCourseTestQ);
			}
			return instructorcoursetestq;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addInstructorCourseTestQ(InstructorCourseTestQ instructorcoursetestq) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblinstructorcortestquestion values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursetestq.getInscourseTestId());
			myStmt.setInt(2, instructorcoursetestq.getQuestionNo());
			myStmt.setString(3, instructorcoursetestq.getQuestion());
			myStmt.setString(4, instructorcoursetestq.getAnswer());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public InstructorCourseTestQ getInstructorCourseTestQ(int instructorcoursetestqid) throws Exception {
		InstructorCourseTestQ instructorcoursetestq = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblinstructorcortestquestion where inscortestquestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursetestqid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("inscortestquestid");
				int inscourseTestId = myRs.getInt("inscoursetestid");
				int questionNo = myRs.getInt("questionno");
				String question = myRs.getString("question");
				String answer = myRs.getString("answer");
				
				instructorcoursetestq = new InstructorCourseTestQ(id,inscourseTestId,questionNo,question,answer);
			}
			else {
				throw new Exception("Could not find instructorcoursetestqid id: " + instructorcoursetestqid);
			}

			return instructorcoursetestq;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateInstructorCourseTestQ(InstructorCourseTestQ instructorcoursetestq) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblinstructorcortestquestion "
						+ " set inscoursetestid=?,questionno=?,question=?,answer=? "
						+ " where inscortestquestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursetestq.getInscourseTestId());
			myStmt.setInt(2, instructorcoursetestq.getQuestionNo());
			myStmt.setString(3, instructorcoursetestq.getQuestion());
			myStmt.setString(4, instructorcoursetestq.getAnswer());
			myStmt.setInt(5, instructorcoursetestq.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteInstructorCourseTestQ(int instructorcoursetestqid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblinstructorcortestquestion where inscortestquestid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, instructorcoursetestqid);
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
