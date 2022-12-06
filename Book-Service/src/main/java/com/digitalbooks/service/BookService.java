package com.digitalbooks.service;

import java.util.List;
import java.util.Map;

import com.digitalbooks.model.Book;
import com.digitalbooks.request.CreateBookRequest;

public interface BookService {

	Book createBook(CreateBookRequest createBookRequest, Long authorID);

	List<Book> searchBook(Map<String, String> allFilter);

}
