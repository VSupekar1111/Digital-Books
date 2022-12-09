package com.digitalbooks.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.payload.request.CreateBookRequest;

public interface BookService {

	ResponseEntity<?> callSearchBookAPI(Map<String, String> allFilter) throws BookServiceException;

	ResponseEntity<?> callCreateBookAPI(@Valid CreateBookRequest createBookRequest, String authorID, MultipartFile file)
			throws BookServiceException, Exception;

}
