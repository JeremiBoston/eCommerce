package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import bean.BookBean;
import bean.ReviewBean;
import bean.UserBean;
import ctrl.Start;
import dao.BookDAO;
import dao.PurchaseDAO;
import dao.ReviewDAO;
import dao.UserDAO;
import dao.VisitDAO;

public class SIS {

	private BookDAO bookDAO;
	private PurchaseDAO purchaseDAO;
	private ReviewDAO reviewDAO;
	private UserDAO userDAO;
	private VisitDAO visitDAO;

	public SIS() throws ClassNotFoundException {
		super();
		this.bookDAO = new BookDAO();
		this.purchaseDAO = new PurchaseDAO();
		this.reviewDAO = new ReviewDAO();
		this.userDAO = new UserDAO();
		this.visitDAO = new VisitDAO();
	}

	public Map<String, BookBean> retrieveAllBooks() {

		try {
			return bookDAO.retrieveAllBooks();
		} catch (SQLException e) {
			return null;
		}

	}

	public void addNewUser(String email, String password, String fname, String lname, String address, String province,
			String country, String zip, String phone) {
		
		this.userDAO.addNewUser(email, password, fname, lname, address, province, country, zip, phone);
		
	}
	
	public boolean addNewReview(String email, String author, String rating, String review, String title) throws NumberFormatException, SQLException {
		
		return this.reviewDAO.addReview(title, author,email,Integer.parseInt(rating),review);
		
	}	
	
	public boolean addNewOrder(String email, ArrayList<String> cart) throws SQLException{
		this.createPurchaseEvents(cart);
		return this.purchaseDAO.submitOrder(email, cart);
	}
	
	public void updateStatus() throws SQLException{
		this.purchaseDAO.updateStatus();
	}
	
	public void createPurchaseEvents(ArrayList<String> cart){
		for (String item : cart) {

			int indexBy = item.indexOf("by");

			String bookname = item.substring(0, indexBy - 1);

			int indexDash = item.indexOf("-");

			String author = item.substring(indexBy + 3, indexDash - 1);

			this.createVisitEvent(bookname, author, "PURCHASE");
		}
	}
	
	public boolean loginUser(String email, String password) throws SQLException{
		
		UserBean result = this.userDAO.loginUser(email, password);
		
		if(result == null){
			return false;
		}else{
			return true;
		}
		
	}
	
	public Map<String,BookBean> retrieveAllResults(String search){
		try {
			return bookDAO.retrieveBySearch(search);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public Map<String,ReviewBean> retrieveByBook(String title){
		try {
			return reviewDAO.retrieveByBook(title);
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean reviewExists(String title, String email) throws SQLException {
		return reviewDAO.reviewExists(title, email);
	}
	
	public void createVisitEvent(String title, String author, String event){
		visitDAO.createVisitEvent(title, author, event);
	}
	
	public List<String[]> getAllPurchasesByEmail(){
		return visitDAO.getAllPurchasesByEmail();
	}
	
	public List<String[]> getTotalPurchasedByEmail(){
		return visitDAO.getTotalPurchasedByEmail();
	}
	
	public List<String[]> getMostBoughtBooks(){
		return visitDAO.getMostBoughtBooks();
	}
	
	public List<String[]> getMostPopularBooks(){
		return visitDAO.getMostPopularBooks();
	}
	
	
	
	

	
}
