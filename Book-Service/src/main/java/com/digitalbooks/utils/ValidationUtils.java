package com.digitalbooks.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.model.Book;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.request.CreateBookRequest;

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

	public void validatesearchRequest(Map<String, String> allFilter) throws BookServiceException {
		List<String> reqParamList=List.of("category","title","authorId","price","publisher");
		if(allFilter.isEmpty())
		{
			throw new BookServiceException("Need at least 1 Filter(like-category/title/authorId/price/publisher");
		}else{
			for(Map.Entry<String,String> param : allFilter.entrySet()){
				if(!reqParamList.contains(param.getKey())) {
					throw new BookServiceException("Invalid Parameter");
				}else {
					if(StringUtils.isEmpty(param.getValue()))
						throw new BookServiceException("Parameter('"+param.getKey()+"') Should Not be blank");	
					 	
				}
			}
		}
		
	}

}
