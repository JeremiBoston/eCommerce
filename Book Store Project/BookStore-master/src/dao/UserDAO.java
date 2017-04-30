package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.UserBean;

public class UserDAO {
	DataSource ds;

	public UserDAO() throws ClassNotFoundException {

		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private boolean checkUserExists(String email) throws SQLException {

		boolean exists = false;

		String checkUserExists = "select count(*) as count from users where email='" + email + "'";

		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(checkUserExists);
		ResultSet r = p.executeQuery();

		int count = 0;

		while (r.next()) {
			count = r.getInt("count");
		}

		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public UserBean addNewUser(String email, String password, String fname, String lname, String street,
			String province, String country, String zip, String phone) {
		UserBean user = null;
		try {
			Connection con = this.ds.getConnection();
			Statement st = con.createStatement();

			// before adding the user, check to see if the user already exists

			if (checkUserExists(email)) {
				// if the user already exists, then do not add them to the database
				
				System.out.println("user already exists");
				return null;
			}

			// Insert a new user into the Users table
			String updateUser = "INSERT INTO Users (fname, lname, email, password) VALUES ('" + fname + "', '" + lname
					+ "', '" + email + "', '" + password + "')";
			st.executeUpdate(updateUser);
			st.close();

			user = new UserBean(fname, lname, email, password);

			Statement st1 = con.createStatement();

			// Add the address to the database connected to the uid
			String updateAdd = "INSERT INTO Address (email, street, province, country, zip, phone) VALUES ('" + email
					+ "', '" + street + "', '" + province + "', '" + country + "', '" + zip + "' ,'" + phone + "')";
			st1.executeUpdate(updateAdd);
			st1.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public UserBean loginUser(String email, String password) throws SQLException {
		UserBean user = null;

		String query = "select * from users where email = '" + email + "' and password = '" + password + "'";

		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			String fname = r.getString("fname");
			String lname = r.getString("lname");

			user = new UserBean(fname, lname, email, password);
		}

		r.close();
		p.close();
		con.close();

		return user;
	}

}
