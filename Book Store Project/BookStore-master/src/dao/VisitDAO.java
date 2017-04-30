package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class VisitDAO {
private DataSource ds;
	
	public VisitDAO() throws ClassNotFoundException{
		try{
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	public void createVisitEvent(String title, String author, String event){
		Connection con;
		try {
			con = this.ds.getConnection();
			Statement stmt = con.createStatement();
			String timeStamp = new SimpleDateFormat("MMddyyyyHHmmss").format(Calendar.getInstance().getTime());

			String sql = "INSERT INTO VisitEvent (day, title, author, eventtype) VALUES ('" 
					+ timeStamp + "', '" +  title + "', '" + author + "', '" + event + "')";
			stmt.executeUpdate(sql);
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	
	public List<String[]> getAllPurchasesByEmail(){
		String query = "select P.email, B.title, B.author, sum(B.price * I.quantity) as TOTAL"
							+ " from PO P, POItem I, Book B "
							+ " where P.id = I.id and B.title = I.title and B.author = I.author "
							+ " group by P.email, B.title, B.author "
							+ "order by P.email";
		
		List<String[]> result = null;
		try {
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			result = new ArrayList<String[]>();
			while(r.next()){
				String email = r.getString("email");
				String title = r.getString("title");
				String author = r.getString("author");
				float total = r.getFloat("total");
				String[] array = {email, title, author, Float.toString(total)};
				result.add(array);
			}
			r.close();
			p.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public List<String[]> getTotalPurchasedByEmail(){
		String query = "select C.email, sum(TOTAL) as total"
				+ " from (select P.email, B.title, B.author, sum(B.price * I.quantity) as TOTAL "
				+ " from PO P, POItem I, Book B " 
				+ " where P.id = I.id and B.title = I.title and B.author = I.author "
				+ " group by P.email, B.title, B.author) as C "	
				+ " group by C.email";
		
		List<String[]> result = null;
		try {
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			result = new ArrayList<String[]>();
			while(r.next()){
				String email = r.getString("email");
				float total = r.getFloat("total");
				String[] array = {email, Float.toString(total)};
				result.add(array);
			}
			r.close();
			p.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	public List<String[]> getMostBoughtBooks(){
		String query = "select PI.title, PI.author, sum(PI.quantity) as TOTAl"
						+ " from POItem PI "
						+ " group by PI.title, PI.author "
						+ " order  by PI.title, PI.author ";
		List<String[]> result = null;
		try {
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			result = new ArrayList<String[]>();
			while(r.next()){
				String title = r.getString("title");
				String author = r.getString("author");
				int total = r.getInt("total");
				String[] array = {title, author, Integer.toString(total)};
				result.add(array);
			}
			r.close();
			p.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public List<String[]> getMostPopularBooks(){
		String query = "select V.title, V.author, count(*) as TOTAl "
						+ "from VisitEvent V "
						+ "group by V.title, V.author "
						+ "order by v.title, V.author";
		List<String[]> result = null;
		try {
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			result = new ArrayList<String[]>();
			while(r.next()){
				String title = r.getString("title");
				String author = r.getString("author");
				int total = r.getInt("total");
				String[] array = {title, author, Integer.toString(total)};
				result.add(array);
			}
			r.close();
			p.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
