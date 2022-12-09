package com.digitalbooks.response;

import java.util.ArrayList;
import java.util.List;

import com.digitalbooks.model.Book;

public class BookServiceResponse {

	String status;
	List<Book> bookList;
	String message;

	public BookServiceResponse() {
		this.bookList=new ArrayList<Book>();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BookServiceResponse [status=" + status + ", bookList=" + bookList + ", message=" + message + "]";
	}

}
