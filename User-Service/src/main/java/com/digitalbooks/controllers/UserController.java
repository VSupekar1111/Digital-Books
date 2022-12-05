package com.digitalbooks.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.exception.UserServiceException;
import com.digitalbooks.payload.request.LoginRequest;
import com.digitalbooks.payload.request.SignupRequest;
import com.digitalbooks.payload.response.JwtResponse;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.service.UserService;
import com.digitalbooks.utils.ValidationUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	ValidationUtils validationUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		JwtResponse jwtResponse = userService.authenticateUser(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws UserServiceException {
		validationUtils.validateRequest(signUpRequest);
		MessageResponse messageResponse= userService.registerUser(signUpRequest);
		return ResponseEntity.ok(messageResponse);
	}

 
}
