package com.digitalbooks.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.model.Book;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.response.BookServiceResponse;
import com.digitalbooks.service.BookService;
import com.digitalbooks.utils.ValidationUtils;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	ValidationUtils validationUtils;

	@Override
	public Book createBook(CreateBookRequest createBookRequest, Long authorID) {
		Book book = new Book();
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

	@Override
	public BookServiceResponse searchBook(Map<String, String> allFilter) {
		List<Book> listBook = new ArrayList<>();
		BookServiceResponse bookServiceResponse = new BookServiceResponse();

		if (validationUtils.validatesearchRequest(allFilter, bookServiceResponse)) {
			if (allFilter.get("category") != null) {
				listBook.addAll(bookRepository.findByCategory(allFilter.get("category")));
			} else if (allFilter.get("title") != null) {
				listBook.addAll(bookRepository.findByTitle(allFilter.get("title")));
			} else if (allFilter.get("authorId") != null) {
				listBook.addAll(bookRepository.findByAuthorId(Long.valueOf(allFilter.get("authorId"))));
			} else if (allFilter.get("price") != null) {
				listBook.addAll(bookRepository.findByPrice(allFilter.get("price")));
			} else if (allFilter.get("publisher") != null) {
				listBook.addAll(bookRepository.findByPublisher(allFilter.get("publisher")));
			}

			List<Book> finalBookList = listBook.stream().filter(book -> {
				if (allFilter.get("category") != null && !allFilter.get("category").equals(book.getCategory()))
					return false;
				if (allFilter.get("title") != null && !allFilter.get("title").equals(book.getTitle()))
					return false;
				if (allFilter.get("authorId") != null
						&& !Long.valueOf(allFilter.get("authorId")).equals(book.getAuthorId()))
					return false;
				if (allFilter.get("price") != null && !allFilter.get("price").equals(book.getCategory()))
					return false;
				if (allFilter.get("publisher") != null && !allFilter.get("publisher").equals(book.getCategory()))
					return false;
				return true;
			}).collect(Collectors.toList());
			bookServiceResponse.setStatus("Success");
			bookServiceResponse.setBookList(finalBookList);	
		}
		return bookServiceResponse;
	}

}
