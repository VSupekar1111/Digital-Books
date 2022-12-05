package com.digitalbooks.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank(message = "'username' must not be BLANK")
	private String username;

	@NotBlank(message = "'password' must not be BLANK")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
