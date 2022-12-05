package com.digitalbooks.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.model.Book;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.service.BookService; 

@CrossOrigin(origins = "${book.service.origin}", maxAge = 3600)
@RestController@RequestMapping(value={"/digitalbooks"})
@RefreshScope
public class BookControler {

	@Autowired
	BookService bookService;
	
	@PostMapping(value="/author/{author-id}/books",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createBook(@Valid @ModelAttribute CreateBookRequest createBookRequest,@RequestParam("file") MultipartFile logo,@PathVariable("author-id") Long authorID) throws IllegalStateException, IOException {
		createBookRequest.setLogo(logo);
		Book book=bookService.createBook(createBookRequest,authorID);
		return ResponseEntity.ok(book);
	}
}
