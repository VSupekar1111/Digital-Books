package com.digitalbooks.payload.response;

import java.time.LocalDateTime;

 
public class BookSubscribe {
 
	Long subscribeId;
 
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
