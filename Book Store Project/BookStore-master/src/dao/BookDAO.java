package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.BookBean;

public class BookDAO {

	private DataSource ds;

	public BookDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, BookBean> runQuery(String query) throws SQLException {

		Map<String, BookBean> rv = new HashMap<String, BookBean>();

		Connection con;
		try {

			con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			int counter = 0;

			while (r.next()) {

				String title = r.getString("TITLE");
				String price = r.getString("PRICE");
				String category = r.getString("CATEGORY");
				String author = r.getString("AUTHOR");
				String picture = r.getString("PICTURE");

				BookBean tmp = new BookBean(title, author, price, category, picture);

				String counterString = Integer.toString(counter);

				rv.put(counterString, tmp);

				counter++;
			}

			r.close();
			p.close();
			con.close();

			return rv;
		} catch (SQLException e) {
			System.out.println("Error in BookDAO");
			return null;
		}

	}

	public Map<String, BookBean> retrieveFromCategory(String category) throws SQLException {
		String query = "select * from book where category =" + category;
		return runQuery(query);

	}

	public Map<String, BookBean> retrieveByTitle(String title) throws SQLException {

		String query = "select * from book where title like '%" + title + "%'";
		return runQuery(query);

	}

	public Map<String, BookBean> retrieveBySearch(String param) throws SQLException {

		String query = "select * from book where LOWER(title) like LOWER('%" + param
				+ "%') or LOWER(author) like LOWER('%" + param + "%') or LOWER(category) like LOWER('%" + param +"%')";
		return runQuery(query);

	}

	public Map<String, BookBean> retrieveAllBooks() throws SQLException {
		String query = "select * from book";
		return runQuery(query);

	}

	public Map<String, BookBean> retrieveByPriceRange(int start, int end) throws SQLException {
		String query = "select * from book where price >= " + start + "and price <= " + end;
		return runQuery(query);

	}

}
