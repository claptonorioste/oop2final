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

import elearning.modules.Country;

public class CountryDbUtil {
	private static CountryDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/elearning";

	public static CountryDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new CountryDbUtil();
		}
		return instance;
	}

	private CountryDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Country> getCountry() throws Exception{
		List<Country> countrys = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcountry order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("countryid");
				String countryCode = myRs.getString("countrycode");
				String countryName = myRs.getString("countryname");

				Country tempCountry = new Country( id, countryCode, countryName);
				countrys.add(tempCountry);
			}
			return countrys;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public List<String> getCountrys() throws Exception{
		List<String> countrys = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcountry order by last_name";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				String countryName = myRs.getString("countryname");
				countrys.add(countryName);
			}
			return countrys;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addCountry(Country country) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "INSERT INTO tblutilcountry values (?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, country.getCountryCode());
			myStmt.setString(2, country.getCountryName());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public Country getCountry(int countryid) throws Exception {
		Country country = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = getConnection();
			String sql = "select * from tblutilcountry where inscourseid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, countryid);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("countryid");
				String countryCode = myRs.getString("countrycode");
				String countryName = myRs.getString("countryname");

				country =  new Country( id, countryCode, countryName);
			}
			else {
				throw new Exception("Could not find student id: " + countryid);
			}

			return country;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateCountry(Country country) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "update tblutilcountry "
						+ " set countrycode=?, countryname=? "
						+ " where countryid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, country.getCountryCode());
			myStmt.setString(2, country.getCountryName());
			myStmt.setInt(3, country.getId());
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteCountry(int countryId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = getConnection();
			String sql = "delete from tblutilcountry where countryid=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, countryId);
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
