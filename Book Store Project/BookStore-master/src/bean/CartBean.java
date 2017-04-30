package bean;

public class CartBean {

	private String bookPicture;
	private String title;
	private int quantity;
	private int price;
	
	public CartBean(String bookPicture, String title, int quantity, int price){
		this.bookPicture = bookPicture;
		this.title = title;
		this.quantity = quantity;
		this.price = price;
	}

	public String getbookPicture() {
		return bookPicture;
	}
	

	public void setbookPicture(String bookPicture) {
		this.bookPicture = bookPicture;
	}

	public String getname() {
		return title;
	}

	public void setname(String name) {
		this.title = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
