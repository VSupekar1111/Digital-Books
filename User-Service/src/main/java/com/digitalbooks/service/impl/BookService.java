package com.digitalbooks.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.digitalbooks.exception.BackeEndServiceException;
import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.models.User;
import com.digitalbooks.payload.request.CreateBookRequest;
import com.digitalbooks.payload.request.SubscribeBookRequest;
import com.digitalbooks.payload.response.Book;
import com.digitalbooks.payload.response.BookServiceResponse;
import com.digitalbooks.payload.response.BookSubscribe;
import com.digitalbooks.payload.response.BookSubscribeResponse;
import com.digitalbooks.repository.UserRepository;

@Service
public class BookService implements com.digitalbooks.service.BookService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserRepository userRepository;

	@Value("${book-service.host}")
	String bookServiceHost;

	@Value("${book-service.httpSchema}")
	String httpSchema;

	@Value("${book-service.search-book}")
	String searchBookAPI;

	@Value("${book-service.create-book}")
	String createBookAPI;

	@Value("${book-service.subscribe-book}")
	String subscribeBookAPI;

	@Value("${book-service.get-subscribed-book}")
	String getSubscribedBookAPI;

	@Override
	public BookServiceResponse callSearchBookAPI(Map<String, String> allFilter)
			throws BookServiceException, BackeEndServiceException {
		System.out.println("Calling Search Book API:");
		boolean isFirstParam = true;
		StringBuilder url = new StringBuilder(searchBookAPI);
		for (Map.Entry<String, String> param : allFilter.entrySet()) {
			if (param.getKey().equalsIgnoreCase("author")) {
				Optional<User> user = userRepository.findByUsername(param.getValue());
				Long value = user.get().getId() != null ? user.get().getId() : 0L;
				if (isFirstParam)
					url.append("?authorId=" + String.valueOf(value));
				else
					url.append("&authorId=" + String.valueOf(value));
				isFirstParam = false;
			} else {
				if (isFirstParam)
					url.append("?" + param.getKey() + "=" + param.getValue());
				else
					url.append("&" + param.getKey() + "=" + param.getValue());
				isFirstParam = false;
			}
		}
		System.out.println("Search Book API URL:" + url);
		ResponseEntity<?> bookServicreResponseEntity = restTemplate.getForEntity(url.toString(),
				BookServiceResponse.class, allFilter);
		BookServiceResponse bookServiceResponse = (BookServiceResponse) bookServicreResponseEntity.getBody();

		if (bookServiceResponse != null) {
			if (bookServiceResponse.getStatus() != null
					&& bookServiceResponse.getStatus().toLowerCase().equals("failure"))
				throw new BookServiceException(bookServiceResponse.getMessage());

			else if (!bookServiceResponse.getBookList().isEmpty()) {
				List<Book> bookList = bookServiceResponse.getBookList().stream().map(book -> {
					Optional<User> user = userRepository.findById(book.getAuthorId());
					if (!user.isEmpty())
						book.setAuthorName(user.get().getusername());
					return book;
				}).collect(Collectors.toList());
				bookServiceResponse.setBookList(bookList);
			}
		} else {
			throw new BackeEndServiceException("Backend Service Failure");
		}
		System.out.println("Book Service Response:" + bookServiceResponse);
		return bookServiceResponse;

	}

	@Override
	public String callCreateBookAPI(@Valid CreateBookRequest createBookRequest, String authorID,
			MultipartFile file) throws BookServiceException, BackeEndServiceException {
		System.out.println("Calling create Book API");
		prepareRequest(createBookRequest, file);

		UriComponents uri = UriComponentsBuilder.newInstance().scheme(httpSchema).host(bookServiceHost)
				.path(createBookAPI).buildAndExpand(authorID);
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
		} else {
			throw new BackeEndServiceException("Backend Service Failure");
		}
		return bookServiceResponse.getMessage();
	}

	private void prepareRequest(@Valid CreateBookRequest createBookRequest, MultipartFile file) {
		createBookRequest.setLogo(file.getOriginalFilename());
	}

	@Override
	public String subscribeBook(SubscribeBookRequest subscribeBookRequest, Long bookId)
			throws BookServiceException, BackeEndServiceException {
		System.out.println("Calling subscribe Book API");
		UriComponents uri = UriComponentsBuilder.newInstance().scheme(httpSchema).host(bookServiceHost)
				.path(subscribeBookAPI).buildAndExpand(bookId);
		System.out.println("Create Book API URL: " + uri);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<SubscribeBookRequest> entity = new HttpEntity<SubscribeBookRequest>(subscribeBookRequest, headers);

		ResponseEntity<?> bookServicreResponseEntity = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity,
				BookSubscribeResponse.class);

		BookSubscribeResponse bookSubscribeResponse = (BookSubscribeResponse) bookServicreResponseEntity.getBody();

		if (bookSubscribeResponse != null) {
			if (bookSubscribeResponse.getStatus() != null
					&& bookSubscribeResponse.getStatus().toLowerCase().equals("failure"))
				throw new BookServiceException(bookSubscribeResponse.getMessage());
		} else {
			throw new BackeEndServiceException("Backend Service Failure");
		}

		return bookSubscribeResponse.getMessage();
	}

	@Override
	public BookSubscribeResponse getBooks(Long userID) throws BookServiceException, BackeEndServiceException {
		System.out.println("Calling get Subscribed Book API");
		UriComponents uri = UriComponentsBuilder.newInstance().scheme(httpSchema).host(bookServiceHost)
				.path(getSubscribedBookAPI).buildAndExpand(userID);
		System.out.println("Subscribed Book API URL: " + uri);

		ResponseEntity<?> bookServicreResponseEntity = restTemplate.getForEntity(uri.toString(),
				BookSubscribeResponse.class);
		BookSubscribeResponse bookSubscribeResponse = (BookSubscribeResponse) bookServicreResponseEntity.getBody();

		if (bookSubscribeResponse != null) {
			if (bookSubscribeResponse.getStatus() != null
					&& bookSubscribeResponse.getStatus().toLowerCase().equals("failure"))
				throw new BookServiceException(bookSubscribeResponse.getMessage());
			else if (bookSubscribeResponse.getBookSubscribeResponseList().size() > 0) {
				List<BookSubscribe> bookSubscribeResponseList = bookSubscribeResponse.getBookSubscribeResponseList()
						.stream().map(subscribedbook -> {
							Optional<User> user = userRepository.findById(subscribedbook.getBook().getAuthorId());
							if (!user.isEmpty())
								subscribedbook.getBook().setAuthorName(user.get().getusername());
							return subscribedbook;
						}).collect(Collectors.toList());
				bookSubscribeResponse.setBookSubscribeResponseList(bookSubscribeResponseList);
			}
		} else {
			throw new BackeEndServiceException("Backend Service Failure");
		}
		return bookSubscribeResponse;
	}
}
