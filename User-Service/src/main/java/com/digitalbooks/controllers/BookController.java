package com.digitalbooks.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.digitalbooks.payload.response.BookSubscribeResponse;
import com.digitalbooks.service.BookService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = { "/digitalbooks" })
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/search")
	public ResponseEntity<?> searchBook(@RequestParam Map<String, String> allFilter) throws BookServiceException, BackeEndServiceException {
		return bookService.callSearchBookAPI(allFilter);
	}

	@PostMapping(value = "/author/{author-id}/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> createBook(@Valid @ModelAttribute CreateBookRequest createBookRequest,
			@RequestParam("file") MultipartFile file, @PathVariable("author-id") String authorID) throws BackeEndServiceException, BookServiceException {
		return bookService.callCreateBookAPI(createBookRequest, authorID, file);
	}

	@PostMapping(value = "/{book-id}/subscribe")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<?> subscribeBook(@RequestBody SubscribeBookRequest subscribeBookRequest,
			@PathVariable("book-id") Long bookId) throws BookServiceException, BackeEndServiceException {
		BookSubscribeResponse bookSubscribeResponse = bookService.subscribeBook(subscribeBookRequest, bookId);
		return ResponseEntity.ok(bookSubscribeResponse);
	}

	@GetMapping(value = "/readers/{user-id}/books")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<?> getBooks(@PathVariable("user-id") Long userID) throws BookServiceException, BackeEndServiceException {
		BookSubscribeResponse bookSubscribeResponse = bookService.getBooks(userID);

		return ResponseEntity.ok(bookSubscribeResponse);
	}

}
