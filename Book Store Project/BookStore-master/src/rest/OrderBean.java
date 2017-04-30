package rest;

public class OrderBean {
	private int bid;
	private String title;
	private String author;
	private float price;
	private int quantity;
	private int poId;
	private String status;
	private String email;
	private String street;
	private String province;
	private String country;
	private String zip;
	
	public OrderBean(int bid, String title, String author, float price, int quantity, int poId, String status,
			String email, String street, String province, String country, String zip) {
		super();
		this.bid = bid;
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
		this.poId = poId;
		this.status = status;
		this.email = email;
		this.street = street;
		this.province = province;
		this.country = country;
		this.zip = zip;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
	
	
}
