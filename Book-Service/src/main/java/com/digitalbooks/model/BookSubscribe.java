package com.digitalbooks.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "bookDB", name = "Book_Subscribe")
public class BookSubscribe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long subscribeId;

	@ManyToOne
	@JoinColumn(name = "Book_id")
	Book book;

	Long userId;

	LocalDateTime subscribeDate;

	boolean isActive;

	public Long getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(Long subscribeId) {
		this.subscribeId = subscribeId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getSubscribeDate() {
		return subscribeDate;
	}

	public void setSubscribeDate(LocalDateTime subscribeDate) {
		this.subscribeDate = subscribeDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "BookSubscribe [subscribeId=" + subscribeId + ", book=" + book + ", userId=" + userId
				+ ", subscribeDate=" + subscribeDate + ", isActive=" + isActive + "]";
	}

}
