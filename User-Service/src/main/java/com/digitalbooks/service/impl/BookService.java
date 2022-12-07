package com.digitalbooks.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.response.BookServiceResponse;

@Service
public class BookService implements com.digitalbooks.service.BookService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${book-service.search-book}")
	String searchBookAPI;

	@Override
	public ResponseEntity<?> callSearchBookAPI(Map<String, String> allFilter) throws BookServiceException {
		boolean isFirstParam = true;
		StringBuilder url = new StringBuilder(searchBookAPI);
		for (Map.Entry<String, String> param : allFilter.entrySet()) {
			if (isFirstParam)
				url.append("?" + param.getKey() + "=" + param.getValue());
			else
				url.append("&" + param.getKey() + "=" + param.getValue());
			isFirstParam = false;
		}
		System.out.println("Search Book URL:" + url);
		ResponseEntity<?> bookServicreResponseEntity = restTemplate.getForEntity(url.toString(),
				BookServiceResponse.class, allFilter);
		BookServiceResponse bookServiceResponse = (BookServiceResponse) bookServicreResponseEntity.getBody();

		if (bookServiceResponse != null) {
			if (bookServiceResponse.getStatus() != null
					&& bookServiceResponse.getStatus().toLowerCase().equals("failure"))
				throw new BookServiceException(bookServiceResponse.getMessage());
		}
		return bookServicreResponseEntity;

	}
}
