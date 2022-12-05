package com.digitalbooks.payload.request;

import java.io.File;

import javax.validation.constraints.NotBlank;

public class CreateBookRequest {

	
	File logo;

	@NotBlank(message = "'title' must not be BLANK")
	String title;

	@NotBlank(message = "'category' must not be BLANK")
	String category;

	@NotBlank(message = "'price' must not be BLANK")
	String price;

	@NotBlank(message = "'author' must not be BLANK")
	String author;

	@NotBlank(message = "'publisher' must not be BLANK")
	String publisher;

	@NotBlank(message = "'publishDate' must not be BLANK")
	String publishDate;

	@NotBlank(message = "'content' must not be BLANK")
	String content;

	boolean active;

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
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

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
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

}
