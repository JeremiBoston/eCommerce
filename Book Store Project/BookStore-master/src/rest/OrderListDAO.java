package rest;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Path("/OrderByPart")
public class OrderListDAO {
	private DataSource ds;

	public OrderListDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<OrderBean> getOrdersByPartNumberDAO(int bid) throws SQLException{
		String query = "select A.email, B.title, B.author, B.price, PI.quantity, P.id as POID, P.status, A.street, A.province, A.country, A.zip "
						+ " from BOOK B, PO P, POITEM PI, ADDRESS A"
						+ " where B.id =" + bid + " and B.title = PI.title and B.author = PI.author "
						+ " and P.id = PI.id and P.email = A.email and P.address = A.id "
						+ " order by A.email, B.title, B.author, B.price, PI.quantity, P.id, P.status, A.street, A.province, A.country, A.zip";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		ArrayList<OrderBean> orders = new ArrayList<OrderBean>();
		while(r.next()){
			String email = r.getString("email");
			String title = r.getString("title");
			String author = r.getString("author");
			float price = r.getFloat("price");
			int quantity = r.getInt("quantity");
			int poId = r.getInt("poid");
			String status = r.getString("status");
			String street = r.getString("street");
			String province = r.getString("province");
			String country = r.getString("country");
			String zip = r.getString("zip");
			OrderBean bean = new OrderBean(bid, title, author, price, quantity, poId, status, email, street, province, country, zip);
			orders.add(bean);
		}
		r.close();
		p.close();
		con.close();
		return orders;
	}
	
	@GET
	@Path("/product/{productId}")
	@Produces("application/xml")
	public String getOrdersByPartNumber(@PathParam("productId") String id) throws Exception{
		int intId = Integer.parseInt(id);
		OrderWrapper orderWrapper = new OrderWrapper(intId, this.getOrdersByPartNumberDAO(intId));
		JAXBContext jc = JAXBContext.newInstance(orderWrapper.getClass());
		Marshaller marshaller = jc.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		
		sw.write("<?xml version='1.0' encoding='UTF-8'?>");
		sw.write("\n");
		sw.write("<?xml-stylesheet type='text/xsl' href='SIS.xsl' ?>");
		sw.write("\n");
		marshaller.marshal(orderWrapper, new StreamResult(sw));
		String result = sw.toString();
		sw.close();
		return result;

		
	}
	
}
