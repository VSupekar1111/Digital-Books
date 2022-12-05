package com.digitalbooks.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
 
public class SignupRequest {
    @NotBlank(message ="'username' must not be BLANK")
    @Size(min = 3, max = 20)
    private String username;
 
  
    @Size(max = 50)
    @Email
    @NotBlank(message ="'email' must not be BLANK")
    private String email;
    
   
    @NotBlank(message ="'role' must not be BLANK")
    private String role;
    
    private String phoneNumber;
    
    @NotBlank(message ="'password' must not be BLANK")
    @Size(min = 8,message="'password' length shoulkd be greater than or equal to 8")
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
    
}
