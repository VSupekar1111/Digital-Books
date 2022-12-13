package com.digitalbooks.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.exception.BackeEndServiceException;
import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.payload.request.CreateBookRequest;
import com.digitalbooks.payload.request.SubscribeBookRequest;
import com.digitalbooks.payload.response.BookServiceResponse;
import com.digitalbooks.payload.response.BookSubscribeResponse;

public interface BookService {

	BookServiceResponse callSearchBookAPI(Map<String, String> allFilter) throws BookServiceException, BackeEndServiceException;

	String callCreateBookAPI(@Valid CreateBookRequest createBookRequest, String authorID, MultipartFile file)
			throws BookServiceException, BackeEndServiceException;

	String subscribeBook(SubscribeBookRequest subscribeBookRequest, Long bookId) throws BookServiceException, BackeEndServiceException;

	BookSubscribeResponse getBooks(Long userID) throws BookServiceException, BackeEndServiceException;

}
