package dao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

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

@Path("/OrderByPart")
public class OrderRest {
private DataSource ds;
	
	public OrderRest() throws ClassNotFoundException{
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
				String picture= r.getString("PICTURE");

				BookBean tmp = new BookBean(title, author, price, category, picture);

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
	
	
	@GET
	@Path("/product/{productId}")
	@Produces("application/xml")
	public String retrieveById(@PathParam("productId") String id) throws Exception{
		int intId = Integer.parseInt(id);
		String query = "select * from book where id =" + intId;
		Map<String, BookBean> res = runQuery(query);
		if(res.size()  == 0){
			return "<html lang=\"en\"><body><h1> No Book by this product Id!</h1></body></html>";
		}
		
		BookBean b = res.get(id);
		b.setId(intId);
		
		JAXBContext jc = JAXBContext.newInstance(b.getClass());
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		Schema schema = sf.newSchema(new File("po.xsd"));
		
		Marshaller marshaller = jc.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setSchema(schema);

		StringWriter sw = new StringWriter();
		
		sw.write("<?xml version='1.0' encoding='UTF-8'?>");
		sw.write("\n");
		sw.write("<?xml-stylesheet type='text/xsl' href='SIS.xsl' ?>");
		sw.write("\n");
		marshaller.marshal(b, new StreamResult(sw));
		
		return sw.toString();
		
		// Somehow need to get the file of session to get the export folder and find xsd
				
	}
}
