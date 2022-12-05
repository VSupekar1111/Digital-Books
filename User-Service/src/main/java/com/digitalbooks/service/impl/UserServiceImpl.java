package com.digitalbooks.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.digitalbooks.models.ERole;
import com.digitalbooks.models.Role;
import com.digitalbooks.models.User;
import com.digitalbooks.payload.request.LoginRequest;
import com.digitalbooks.payload.request.SignupRequest;
import com.digitalbooks.payload.response.JwtResponse;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.repository.RoleRepository;
import com.digitalbooks.repository.UserRepository;
import com.digitalbooks.security.services.UserDetailsImpl;
import com.digitalbooks.service.UserService;
import com.digitalbooks.utils.JwtUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public MessageResponse registerUser(@Valid SignupRequest signUpRequest) {

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getEmail(), signUpRequest.getPhoneNumber());

		String role = signUpRequest.getRole();

		if (!role.isBlank()) {

			switch (role.toLowerCase()) {
			case "author":
				Role authorRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				user.setRole(authorRole);
				break;
			case "reader":
				Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				user.setRole(readerRole);
				break;
			default:

			}
		}

		userRepository.save(user);
		return new MessageResponse("User registered successfully!");
	}

	@Override
	public JwtResponse authenticateUser(@Valid LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);

	}

}
