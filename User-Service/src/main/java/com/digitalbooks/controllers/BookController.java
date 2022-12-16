package com.digitalbooks.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.exception.BackeEndServiceException;
import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.payload.request.CreateBookRequest;
import com.digitalbooks.payload.request.SubscribeBookRequest;
import com.digitalbooks.payload.response.BookServiceResponse;
import com.digitalbooks.payload.response.BookSubscribeResponse;
import com.digitalbooks.service.BookService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = { "/digitalbooks" })
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/search")
	public ResponseEntity<?> searchBook(@RequestParam Map<String, String> allFilter)
			throws BookServiceException, BackeEndServiceException {
		BookServiceResponse bookServiceResponse = bookService.callSearchBookAPI(allFilter);
		return ResponseEntity.ok(bookServiceResponse);
	}

	@PostMapping(value = "/author/{author-id}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> createBook(@Valid @RequestBody CreateBookRequest createBookRequest,
			@PathVariable("author-id") String authorID) throws BackeEndServiceException, BookServiceException {
		String response = bookService.callCreateBookAPI(createBookRequest, authorID, null);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "/{book-id}/subscribe")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<?> subscribeBook(@RequestBody SubscribeBookRequest subscribeBookRequest,
			@PathVariable("book-id") Long bookId) throws BookServiceException, BackeEndServiceException {
		String response = bookService.subscribeBook(subscribeBookRequest, bookId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/readers/{user-id}/books")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<?> getBooks(@PathVariable("user-id") Long userID)
			throws BookServiceException, BackeEndServiceException {
		BookSubscribeResponse bookSubscribeResponse = bookService.getBooks(userID);

		return ResponseEntity.ok(bookSubscribeResponse);
	}

	@PostMapping(value = "/readers/{reader-id}/books/{subscription-id}/cancel-subscription")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<?> cancelSubscription(@PathVariable("subscription-id") Long subscriptionId,
			@PathVariable("reader-id") Long readerId) throws BookServiceException, BackeEndServiceException {
		String response = bookService.callcancelSubscription(subscriptionId, readerId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/author/{user-id}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> getAuthorBooks(@PathVariable("user-id") Long userID)
			throws BookServiceException, BackeEndServiceException {
		BookServiceResponse bookServiceResponse = bookService.getAuthorBooks(userID);

		return ResponseEntity.ok(bookServiceResponse);
	}

}
