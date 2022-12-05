package com.digitalbooks.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	schema ="userDB", name = "User", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "Email")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password,
			@NotBlank @Size(max = 50) @Email String email, @Size(max = 15) String phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@Size(max = 20)
	@Column(unique=true,name="User_Name")
	private String username;
	
	@NotBlank
	@Size(max = 120)
	@Column(name="Password")
	private String password;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name="Email")
	private String email;

	
	@Size(max = 15)
	@Column(name="Phone_Number")
	String phoneNumber;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "Role_id")
	private Role role;

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	
}
