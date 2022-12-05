package com.digitalbooks.service;

import com.digitalbooks.model.Book;
import com.digitalbooks.request.CreateBookRequest;

public interface BookService {

	Book createBook(CreateBookRequest createBookRequest, Long authorID);

}
