package com.digitalbooks.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalbooks.model.Book;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.BookSubscribeRepository;
import com.digitalbooks.request.CreateBookRequest;
import com.digitalbooks.request.SubscribeBookRequest;
import com.digitalbooks.response.BookServiceResponse;
import com.digitalbooks.response.BookSubscribeResponse;

@Component
public class ValidationUtils {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookSubscribeRepository bookSubscribeRepository;

	public boolean validateCreateBookRequest(CreateBookRequest createBookRequest, Long authorID,
			BookServiceResponse bookServiceResponse) {
		List<Long> bookList = bookRepository.getBooksByAuthor(createBookRequest.getTitle(), authorID);
		System.out.println(bookList);
		if (!bookList.isEmpty()) {
			bookServiceResponse.setStatus("Failure");
			bookServiceResponse.setMessage("Duplicate Book");
			return false;
		}
		return true;
	}

	public boolean validatesearchRequest(Map<String, String> allFilter, BookServiceResponse bookServiceResponse) {
		List<String> reqParamList = List.of("category", "title", "authorId", "price", "publisher");
		if (allFilter.isEmpty()) {
			bookServiceResponse.setStatus("Failure");
			bookServiceResponse.setMessage("Need at least 1 Filter(like-category/title/authorId/price/publisher");
			return false;
		} else {
			for (Map.Entry<String, String> param : allFilter.entrySet()) {
				if (!reqParamList.contains(param.getKey())) {
					bookServiceResponse.setStatus("Failure");
					bookServiceResponse.setMessage("Invalid Parameter");
					return false;
				} else if (StringUtils.isEmpty(param.getValue())) {
					bookServiceResponse.setStatus("Failure");
					bookServiceResponse.setMessage("Parameter('" + param.getKey() + "') Should Not be blank");
					return false;

				}
			}
		}
		return true;

	}

	public boolean validateSubscribeBookRequest(SubscribeBookRequest subscribeBookRequest, Long bookId,
			BookSubscribeResponse bookSubscribeResponse) {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book.isEmpty()) {
			bookSubscribeResponse.setStatus("Failure");
			bookSubscribeResponse.setMessage("Book not Exist");
			return false;
		} else {
			if (!bookSubscribeRepository
					.findBySubscribedBookAndAuthor(book.get(), subscribeBookRequest.getReader(), true).isEmpty()) {
				bookSubscribeResponse.setStatus("Failure");
				bookSubscribeResponse.setMessage("Already Subscribed");
				return false;
			}
		}

		return true;
	}

}
