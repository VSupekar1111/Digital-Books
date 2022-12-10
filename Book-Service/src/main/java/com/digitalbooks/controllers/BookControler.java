package com.digitalbooks.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.request.SubscribeBookRequest;
import com.digitalbooks.response.BookServiceResponse;
import com.digitalbooks.response.BookSubscribeResponse;
import com.digitalbooks.service.BookService;
import com.digitalbooks.utils.ValidationUtils;

@RestController
@RequestMapping(value = { "/digitalbooks" })
@RefreshScope
public class BookControler {

	@Autowired
	BookService bookService;

	@Autowired
	ValidationUtils validationUtils;

	@PostMapping(value = "/author/{author-id}/books")
	public ResponseEntity<?> createBook(@RequestBody CreateBookRequest createBookRequest,
			@PathVariable("author-id") Long authorID) {
		BookServiceResponse bookServiceResponse = null;
		try {
			bookServiceResponse = bookService.createBook(createBookRequest, authorID);
		} catch (Exception e) {
			System.out.println("Exception Occured: " + e.getMessage());
		}
		return ResponseEntity.ok(bookServiceResponse);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> searchBook(@RequestParam Map<String, String> allFilter) {
		BookServiceResponse bookServiceResponse = bookService.searchBook(allFilter);
		return ResponseEntity.ok(bookServiceResponse);
	}

	@PostMapping(value = "/{book-id}/subscribe")
	public ResponseEntity<?> subscribeBook(@RequestBody SubscribeBookRequest subscribeBookRequest,
			@PathVariable("book-id") Long bookId) {
		BookSubscribeResponse bookServiceResponse = null;
		try {
			bookServiceResponse = bookService.subscribeBook(subscribeBookRequest, bookId);
		} catch (Exception e) {
			System.out.println("Exception Occured: " + e.getMessage());
		}
		return ResponseEntity.ok(bookServiceResponse);
	}
}
