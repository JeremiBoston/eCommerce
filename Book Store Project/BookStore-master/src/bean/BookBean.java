package bean;

public class BookBean {
	private int id;
	private String title;
	private String price;
	private String category;
	private String author;
	private String picture;
	
	public BookBean(String title, String author, String price, String category, String picture) {

		this.title = title;
		this.author = author;
		this.price = price;
		this.category = category;
		this.picture = picture;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return this.price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	
}
