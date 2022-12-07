package com.digitalbooks.service;

import java.util.Map;

import com.digitalbooks.model.Book;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.response.BookServiceResponse;

public interface BookService {

	Book createBook(CreateBookRequest createBookRequest, Long authorID);

	BookServiceResponse searchBook(Map<String, String> allFilter);

}
