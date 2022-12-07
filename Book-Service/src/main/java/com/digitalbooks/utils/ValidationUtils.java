package com.digitalbooks.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.model.Book;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.response.BookServiceResponse;

@Component
public class ValidationUtils {

	@Autowired
	BookRepository bookRepository;

	public void validateCreateBookRequest(CreateBookRequest createBookRequest, Long authorID)
			throws BookServiceException {
		List<Book> bookList = bookRepository.getBooksByAuthor(createBookRequest.getTitle(), authorID);
		System.out.println(bookList);
		if (!bookList.isEmpty()) {
			throw new BookServiceException("Duplicate Book");
		}
	}

	public boolean validatesearchRequest(Map<String, String> allFilter, BookServiceResponse bookServiceResponse) {
		List<String> reqParamList = List.of("category", "title", "authorId", "price", "publisher");
		if (allFilter.isEmpty()) {
			bookServiceResponse.setStatus("Failure");
			bookServiceResponse.setMessage("Need at least 1 Filter(like-category/title/authorId/price/publisher");
			bookServiceResponse.setBookList(new ArrayList<Book>());
			return false;
		} else {
			for (Map.Entry<String, String> param : allFilter.entrySet()) {
				if (!reqParamList.contains(param.getKey())) {
					bookServiceResponse.setStatus("Failure");
					bookServiceResponse.setMessage("Invalid Parameter");
					bookServiceResponse.setBookList(new ArrayList<Book>());
					return false;
				} else if (StringUtils.isEmpty(param.getValue())) {
					bookServiceResponse.setStatus("Failure");
					bookServiceResponse.setMessage("Parameter('"+param.getKey()+"') Should Not be blank");
					bookServiceResponse.setBookList(new ArrayList<Book>());
					return false;

				}
			}
		}
		return true;

	}

}
