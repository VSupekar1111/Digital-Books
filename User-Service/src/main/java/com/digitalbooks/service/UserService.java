package com.digitalbooks.service;

import javax.validation.Valid;

import com.digitalbooks.payload.request.LoginRequest;
import com.digitalbooks.payload.request.SignupRequest;
import com.digitalbooks.payload.response.JwtResponse;
import com.digitalbooks.payload.response.MessageResponse;


public interface UserService {

	MessageResponse registerUser(@Valid SignupRequest signUpRequest);

	JwtResponse authenticateUser(@Valid LoginRequest loginRequest);

}
