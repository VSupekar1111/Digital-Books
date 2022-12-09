package com.digitalbooks.payload.response;

import java.time.LocalDate;
import java.util.Date;

public class Book {

	private Long id;

	String title;

	String category;

	String price;

	String publisher;

	LocalDate publishDate;

	String content;

	String logo;

	Long authorId;

	boolean active;

	LocalDate createDate;

	Date updateDate;

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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", category=" + category + ", price=" + price + ", publisher="
				+ publisher + ", publishDate=" + publishDate + ", content=" + content + ", logo=" + logo + ", authorId="
				+ authorId + ", active=" + active + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}

	
}
