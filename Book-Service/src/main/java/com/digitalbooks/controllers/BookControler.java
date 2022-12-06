package com.digitalbooks.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.model.Book;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.service.BookService;
import com.digitalbooks.utils.ValidationUtils;

@CrossOrigin(origins = "${book.service.origin}", maxAge = 3600)
@RestController
@RequestMapping(value = { "/digitalbooks" })
@RefreshScope
public class BookControler {

	@Autowired
	BookService bookService;

	@Autowired
	ValidationUtils validationUtils;

	@PostMapping(value = "/author/{author-id}/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createBook(@Valid @ModelAttribute CreateBookRequest createBookRequest,
			@RequestParam("file") MultipartFile logo, @PathVariable("author-id") Long authorID)
			throws IllegalStateException, IOException, BookServiceException {
		createBookRequest.setLogo(logo);
		validationUtils.validateCreateBookRequest(createBookRequest, authorID);
		Book book = bookService.createBook(createBookRequest, authorID);
		return ResponseEntity.ok(book);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> searchBook(@RequestParam Map<String, String> allFilter) throws BookServiceException {
		validationUtils.validatesearchRequest(allFilter);
		List<Book> bookList = bookService.searchBook(allFilter);
		return ResponseEntity.ok(bookList);
	}
}
