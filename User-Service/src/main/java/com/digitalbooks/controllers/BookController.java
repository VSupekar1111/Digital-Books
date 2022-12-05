package com.digitalbooks.controllers;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.payload.request.CreateBookRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value={"/digitalbooks"})
public class BookController {
	
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('AUTHOR') or hasRole('READER')")
	public String userAccess() {
		return "Author or Reader Role";
	}

	@PostMapping(value="/author/{author-id}/books",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('AUTHOR')")
	public String createBook(@Valid @ModelAttribute CreateBookRequest createBookRequest,@RequestParam("file") MultipartFile logo,@PathVariable("author-id") String authorID) {
		//RestTemplate to call book service
		return "Create Books(Author Role)";
	}


}
