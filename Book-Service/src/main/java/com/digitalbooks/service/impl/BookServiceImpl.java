package com.digitalbooks.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.model.Book;
import com.digitalbooks.model.BookSubscribe;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.BookSubscribeRepository;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.request.SubscribeBookRequest;
import com.digitalbooks.response.BookServiceResponse;
import com.digitalbooks.response.BookSubscribeResponse;
import com.digitalbooks.service.BookService;
import com.digitalbooks.utils.ValidationUtils;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookSubscribeRepository bookSubscribeRepository;

	@Autowired
	ValidationUtils validationUtils;

	@Override
	public BookServiceResponse createBook(CreateBookRequest createBookRequest, Long authorID) {
		System.out.println("Creating Book");
		BookServiceResponse bookServiceResponse = new BookServiceResponse();
		if (validationUtils.validateCreateBookRequest(createBookRequest, authorID, bookServiceResponse)) {
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
			Book bookResponse = bookRepository.save(book);
			System.out.println("Book created : " + bookResponse);
			bookServiceResponse.setStatus("Success");
			bookServiceResponse.setMessage("id:"+bookResponse.getId());
			return bookServiceResponse;
		}
		return bookServiceResponse;
	}

	@Override
	public BookServiceResponse searchBook(Map<String, String> allFilter) {
		List<Book> listBook = new ArrayList<>();
		System.out.println("Searching Book:");
		BookServiceResponse bookServiceResponse = new BookServiceResponse();

		if (validationUtils.validatesearchRequest(allFilter, bookServiceResponse)) {
			if (allFilter.get("category") != null) {
				listBook.addAll(bookRepository.findByCategory(allFilter.get("category"), true));
			} else if (allFilter.get("title") != null) {
				listBook.addAll(bookRepository.findByTitle(allFilter.get("title"), true));
			} else if (allFilter.get("authorId") != null) {
				listBook.addAll(bookRepository.findByAuthorId(Long.valueOf(allFilter.get("authorId")), true));
			} else if (allFilter.get("price") != null) {
				listBook.addAll(bookRepository.findByPrice(allFilter.get("price"), true));
			} else if (allFilter.get("publisher") != null) {
				listBook.addAll(bookRepository.findByPublisher(allFilter.get("publisher"), true));
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
			bookServiceResponse.setBookList(finalBookList.stream().map(book -> {
				book.setContent("");
				return book;
			}).collect(Collectors.toList()));
		}
		System.out.println("Book Service Response :" + bookServiceResponse);
		return bookServiceResponse;
	}

	@Override
	public BookSubscribeResponse subscribeBook(SubscribeBookRequest subscribeBookRequest, Long bookId) {
		System.out.println("Subscribing Book");
		BookSubscribeResponse bookSubscribeResponse = new BookSubscribeResponse();
		if (validationUtils.validateSubscribeBookRequest(subscribeBookRequest, bookId, bookSubscribeResponse)) {
			Optional<Book> book = bookRepository.findById(bookId);
			BookSubscribe bookSubscribe = new BookSubscribe();
			bookSubscribe.setBook(book.get());
			bookSubscribe.setUserId(subscribeBookRequest.getReader());
			bookSubscribe.setSubscribeDate(LocalDateTime.now());
			bookSubscribe.setActive(true);
			BookSubscribe subscribeResponse = bookSubscribeRepository.save(bookSubscribe);
			bookSubscribeResponse.setStatus("Success");
			bookSubscribeResponse.setMessage(String.valueOf(subscribeResponse.getSubscribeId()));
			System.out.println("Book Subscribe Response :" + bookSubscribeResponse);
			return bookSubscribeResponse;
		}
		return bookSubscribeResponse;
	}

	@Override
	public BookSubscribeResponse getBooks(Long userID) {
		System.out.println("Fetching Subscribeed books");
		BookSubscribeResponse bookSubscribeResponse = new BookSubscribeResponse();
		List<BookSubscribe> subscribeResponse = bookSubscribeRepository.findSubscribedBooksByReader(userID, true);
		bookSubscribeResponse.setStatus("Success");
		bookSubscribeResponse.setBookSubscribeResponseList((subscribeResponse.stream().map(subscribedbook -> {
			subscribedbook.getBook().setContent(""); // Hiding book content
			return subscribedbook;
		}).collect(Collectors.toList())));
		bookSubscribeResponse.setMessage("Subscribed book list");
		return bookSubscribeResponse;

	}

	@Override
	public BookSubscribeResponse cancelSubscription(Long subscriptionId, Long readerId) {
		System.out.println("Cancelling Subscription");
		BookSubscribeResponse bookSubscribeResponse = new BookSubscribeResponse();
		if (validationUtils.validateCancelsubscriptionRequest(subscriptionId, readerId,bookSubscribeResponse)) {
			Optional<BookSubscribe> bookSubscribe =bookSubscribeRepository.findById(subscriptionId);
			if(!bookSubscribe.isEmpty()) {
				bookSubscribe.get().setActive(false);
				bookSubscribeRepository.save(bookSubscribe.get());
			}
			bookSubscribeResponse.setStatus("Success");
			bookSubscribeResponse.setMessage("UnSubscribeId:"+bookSubscribe.get().getSubscribeId());
			System.out.println("Book Subscribe Response :" + bookSubscribeResponse);
			return bookSubscribeResponse;
		}
		 
		return bookSubscribeResponse;
	
	}

}
