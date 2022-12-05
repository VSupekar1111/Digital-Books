package com.digitalbooks.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.model.Book;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.service.BookService;

@Service
public class BookServiceImpl implements BookService{
    
	@Autowired
	BookRepository bookRepository;
	
	@Override
	public Book createBook(CreateBookRequest createBookRequest, Long authorID) {
		Book book=new Book();
		book.setTitle(createBookRequest.getTitle());
		book.setLogo(createBookRequest.getLogo());
		book.setPrice(createBookRequest.getPrice());
		book.setCategory(createBookRequest.getCategory());
		book.setContent(createBookRequest.getContent());
		book.setPublisher(createBookRequest.getPublisher());
		book.setPublishDate(LocalDate.parse(createBookRequest.getPublishDate()));
		book.setAuthorId(authorID);
		book.setActive(createBookRequest.isActive());
		return bookRepository.save(book); 
	}

}
