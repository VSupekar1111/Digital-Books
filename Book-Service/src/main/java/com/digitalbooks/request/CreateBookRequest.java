package com.digitalbooks.request;

public class CreateBookRequest {
	

	String logo;

	String title;

	String category;

	double price;

	String author;

	String publisher;

	String content;

	boolean active;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	 
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

 
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	

//	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
//	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
//	    multipart.transferTo(convFile);
//	    return convFile;
//	}
}
