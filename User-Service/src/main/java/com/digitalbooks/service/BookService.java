package com.digitalbooks.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.digitalbooks.exception.BookServiceException;

public interface BookService {

	ResponseEntity<?> callSearchBookAPI(Map<String, String> allFilter) throws BookServiceException;

}
