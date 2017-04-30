package bean;

public class ReviewBean {

	/*********Attributes************/
	private String title;
	private String author;
	private String fullName;
	private int rating;
	private String review;
	private String email;

	/*********Constructor************/
	public ReviewBean(String title, String author, String email, String fullName, int rating, String review) {
		this.title = title;
		this.author = author;
		this.email = email;
		this.fullName = fullName;
		this.rating = rating;
		this.review = review;
	}
	
	/*********Methods************/
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}	
}
