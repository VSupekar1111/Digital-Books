package com.digitalbooks.service;

import java.util.Map;

import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.request.SubscribeBookRequest;
import com.digitalbooks.response.BookServiceResponse;
import com.digitalbooks.response.BookSubscribeResponse;

public interface BookService {

	BookServiceResponse createBook(CreateBookRequest createBookRequest, Long authorID);

	BookServiceResponse searchBook(Map<String, String> allFilter);

	BookSubscribeResponse subscribeBook(SubscribeBookRequest subscribeBookRequest, Long bookId);

}
