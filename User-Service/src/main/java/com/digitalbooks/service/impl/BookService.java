package com.digitalbooks.service.impl;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.payload.request.CreateBookRequest;
import com.digitalbooks.payload.response.BookServiceResponse;

@Service
public class BookService implements com.digitalbooks.service.BookService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${book-service.search-book}")
	String searchBookAPI;

	@Value("${book-service.create-book}")
	String createBookAPI;
	
	@Value("${book-service.host}")
	String bookServiceHost;
	
	@Value("${book-service.httpSchema}")
	String httpSchema;
	

	@Override
	public ResponseEntity<?> callSearchBookAPI(Map<String, String> allFilter) throws BookServiceException {
		System.out.println("Calling Search Book API:");
		boolean isFirstParam = true;
		StringBuilder url = new StringBuilder(searchBookAPI);
		for (Map.Entry<String, String> param : allFilter.entrySet()) {
			if (isFirstParam)
				url.append("?" + param.getKey() + "=" + param.getValue());
			else
				url.append("&" + param.getKey() + "=" + param.getValue());
			isFirstParam = false;
		}
		System.out.println("Search Book API URL:" + url);
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

	@Override
	public ResponseEntity<?> callCreateBookAPI(@Valid CreateBookRequest createBookRequest, String authorID,
			MultipartFile file) throws Exception {
		System.out.println("Calling create Book API");
		prepareRequest(createBookRequest, file);

		UriComponents uri = UriComponentsBuilder.newInstance().scheme(httpSchema).host(bookServiceHost).path(createBookAPI).buildAndExpand(authorID);
		System.out.println("Create Book API URL: " + uri);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<CreateBookRequest> entity = new HttpEntity<CreateBookRequest>(createBookRequest, headers);

		ResponseEntity<?> bookServicreResponseEntity = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity,
				BookServiceResponse.class);
		BookServiceResponse bookServiceResponse = (BookServiceResponse) bookServicreResponseEntity.getBody();

		if (bookServiceResponse != null) {
			if (bookServiceResponse.getStatus() != null
					&& bookServiceResponse.getStatus().toLowerCase().equals("failure"))
				throw new BookServiceException(bookServiceResponse.getMessage());
		}else {
			throw new Exception("Backend Service Failure");
		}
		return bookServicreResponseEntity;
	}

	private void prepareRequest(@Valid CreateBookRequest createBookRequest, MultipartFile file) {
		createBookRequest.setLogo(file.getOriginalFilename());
	}
}
