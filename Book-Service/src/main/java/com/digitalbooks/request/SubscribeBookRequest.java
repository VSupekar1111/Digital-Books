package com.digitalbooks.request;

public class SubscribeBookRequest {
	Long bookId;
	Long reader;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getReader() {
		return reader;
	}

	public void setReader(Long reader) {
		this.reader = reader;
	}

}
