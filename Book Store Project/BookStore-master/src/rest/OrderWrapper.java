package rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Orders")
public class OrderWrapper {
	@XmlAttribute(name="BID")
	private static int bid;
	@XmlElement(name="orderList")
	private List<OrderBean> list;
	
	public OrderWrapper(int bid, List<OrderBean> list){
		super();
		this.bid = bid;
		this.list = list;
	}
	
	public OrderWrapper(){
		
	}
	
	public List<OrderBean> getList(){
		return this.list;
	}
}
