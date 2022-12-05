package com.digitalbooks.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalbooks.exception.UserServiceException;
import com.digitalbooks.payload.request.SignupRequest;
import com.digitalbooks.repository.RoleRepository;
import com.digitalbooks.repository.UserRepository;

@Component
public class ValidationUtils {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public void validateRequest(@Valid SignupRequest signUpRequest) throws UserServiceException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new UserServiceException("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserServiceException("Error: Email is already in use!");
		}

		if (!(signUpRequest.getRole().equalsIgnoreCase("author")
				|| signUpRequest.getRole().equalsIgnoreCase("reader"))) {
			throw new UserServiceException("Invalid Role");
		}
		
		if(!isValidPassword(signUpRequest.getPassword()))
		{
			throw new UserServiceException("Need Strong Password(min length=8,atleast 1 special char,atleaset 1 upeer case etc.");
		}
	}
	
	public static boolean isValidPassword(String password)
    {

        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
