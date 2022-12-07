package com.digitalbooks.exception.components;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitalbooks.exception.BookServiceException;
import com.digitalbooks.exception.UserServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleException(Exception exception) {
		System.out.println("Exception");
		Map<String, String> errorsMap = new HashMap<>();
		errorsMap.put("Exception", exception.getMessage());
		return errorsMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
		System.out.println("bad request");
		Map<String, String> errorsMap = new HashMap<>();
		ListIterator<ObjectError> iterator = errors.listIterator();
		while (iterator.hasNext()) {
			ObjectError error = iterator.next();
			errorsMap.put(error.getCode(), error.getDefaultMessage());
		}
		return errorsMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserServiceException.class)
	public Map<String, String> handleUserServiceException(UserServiceException exception) {
		System.out.println("bad request");
		Map<String, String> errorsMap = new HashMap<>();
		errorsMap.put("errorMessage", exception.getMessage());
		return errorsMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BookServiceException.class)
	public Map<String, String> handleBookServiceException(BookServiceException exception) {
		System.out.println("bad request");
		Map<String, String> errorsMap = new HashMap<>();
		errorsMap.put("errorMessage", exception.getMessage());
		return errorsMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public Map<String, String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
		System.out.println("IllegalArgumentException");
		Map<String, String> errorsMap = new HashMap<>();
		errorsMap.put("errorMessage", illegalArgumentException.getMessage());
		return errorsMap;
	}

}
