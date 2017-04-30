package dao;

import java.io.File;
import java.io.StringWriter;
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

public class ProductCatalog {
private DataSource ds;
	
	public ProductCatalog() throws ClassNotFoundException{
		try{
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	public Map<String, BookBean> runQuery(String query) throws SQLException{
		
		Map<String, BookBean> rv = new HashMap<String, BookBean>();

		Connection con;
		try {
			
			con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			int counter = 0;
			
			while (r.next()) {
				int id = r.getInt("id");
				String title = r.getString("TITLE") ;
				String price = r.getString("PRICE");
				String category = r.getString("CATEGORY");
				String author = r.getString("AUTHOR");
				String picture  = r.getString("PICTURE");

				BookBean tmp = new BookBean(title, author, price, category, picture);
				tmp.setId(id);
				String counterString = Integer.toString(counter);

				rv.put(Integer.toString(id), tmp);

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
	
	public String getProductInfo(String id) throws Exception{
		int intId = Integer.parseInt(id);
		String query = "select * from book where id =" + intId;
		Map<String, BookBean> res = runQuery(query);
		if(res.size()  == 0){
			return "<html lang=\"en\"><body><h1> No Book by this product Id!</h1></body></html>";
		}
		
		BookBean b = res.get(id);
		b.setId(intId);
		System.out.println(b.getTitle());
		return "<img src='" + b.getPicture() + "' width='200' height='300' />" 
				+ "<h1>" + b.getTitle() + "</h1>" 
				+ "<h2>" + b.getAuthor() + "</h2>"
				+ "<h3>" + b.getPrice() + "</h3>"
				+ "<h4>"+ b.getCategory() + "</h4>";		
	}
}
