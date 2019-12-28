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

import elearning.modules.PaymentType;
public class PaymentTypeDbUtil {
	private static PaymentTypeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static PaymentTypeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new PaymentTypeDbUtil();
		}

		return instance;
	}

	private PaymentTypeDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}
	//return Lists
	public List<PaymentType> getPaymentType() throws Exception{
		List<PaymentType> paymenttype = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilpaymenttype order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("paytypeid");
				String typeCode = myRs.getString("paytypecode");
				String typeName = myRs.getString("paytypename");

				PaymentType tempPaymentType = new PaymentType(id, typeCode, typeName);
				paymenttype.add(tempPaymentType);
			}
			return paymenttype;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Adding
	public void addPaymentType(PaymentType paymenttype) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilpaymenttype values (?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, paymenttype.getTypeCode());
			myStmt.setString(2, paymenttype.getTypeName());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//return data by id
	public PaymentType getPaymentType(int paymenttypeid) throws Exception {
		PaymentType paymenttype = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilpaymenttype where paytypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, paymenttypeid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("paytypeid");
				String dateFrom = myRs.getString("datefrom");
				String dateTo = myRs.getString("dateto");

				paymenttype =  new PaymentType(id, dateFrom, dateTo);
			}
			else {
				throw new Exception("Could not find paymenttypeid id: " + paymenttypeid);
			}

			return paymenttype;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	//Update
	public void updatePaymentType(PaymentType paymenttype) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilpaymenttype "
						+ " set datefrom=?, dateto=? "
						+ " where paytypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, paymenttype.getTypeCode());
			myStmt.setString(2, paymenttype.getTypeName());
			myStmt.setInt(3, paymenttype.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	//Delete
	public void deletePaymentType(int paymenttypeid) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilpaymenttype where paytypeid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, paymenttypeid);
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
