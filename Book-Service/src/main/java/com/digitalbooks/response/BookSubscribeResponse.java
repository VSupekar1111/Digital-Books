package com.digitalbooks.response;

import java.util.ArrayList;
import java.util.List;

import com.digitalbooks.model.BookSubscribe;

public class BookSubscribeResponse {

	String status;
	List<BookSubscribe> bookSubscribeResponseList;
	String message;

	public BookSubscribeResponse(){
		bookSubscribeResponseList=new ArrayList<>();
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public List<BookSubscribe> getBookSubscribeResponseList() {
		return bookSubscribeResponseList;
	}

	public void setBookSubscribeResponseList(List<BookSubscribe> bookSubscribeResponseList) {
		this.bookSubscribeResponseList = bookSubscribeResponseList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "BookSubscribeResponse [status=" + status + ", bookSubscribeResponseList=" + bookSubscribeResponseList
				+ ", message=" + message + "]";
	}

	

}
