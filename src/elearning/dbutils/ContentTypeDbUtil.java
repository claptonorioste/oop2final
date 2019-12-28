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

import elearning.modules.ContentType;
public class ContentTypeDbUtil {
	private static ContentTypeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static ContentTypeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ContentTypeDbUtil();
		}

		return instance;
	}

	private ContentTypeDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<ContentType> getContentType() throws Exception{
		List<ContentType> contenttype = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcontenttype order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("contenttypeid");
				String contentType = myRs.getString("contenttypename");

				ContentType tempContentType = new ContentType(id,contentType);
				contenttype.add(tempContentType);
			}
			return contenttype;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addContentType(ContentType contenttype) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilcontenttype values (?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, contenttype.getContentType());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public ContentType getContentType(int contenttypeid) throws Exception {
		ContentType contenttype = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcontenttype where contenttypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, contenttypeid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("contenttypeid");
				String contentType = myRs.getString("contenttypename");
				
				contenttype = new ContentType(id,contentType);
			}
			else {
				throw new Exception("Could not find contenttypeid id: " + contenttypeid);
			}

			return contenttype;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updateContentType(ContentType contenttype) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilcontenttype "
						+ " set certname=? "
						+ " where contenttypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, contenttype.getContentType());
			myStmt.setInt(2, contenttype.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deleteContentType(int contenttypeid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilcontenttype where contenttypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, contenttypeid);
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
