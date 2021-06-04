package com.xws.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

	@NotEmpty(message = "Email is required.")
	@Email(message = "Email format is not valid.")
	private String email;
	@NotEmpty(message = "Password is required.")
	@Size(min = 6, message = "Password must have at least 6 characters.")
	private String password;
	@NotEmpty(message = "Name is required.")
	private String name;
	@NotEmpty(message = "Surname is required.")
	private String surname;
	@NotEmpty(message = "Username is required.")
	private String username;
	@NotEmpty(message = "Gender is required.")
	private String gender;

	public UserRegisterDTO() {

	}

	public UserRegisterDTO(String email, String password, String name, String surname, String city, String address,
                           String country, String mobile, String username, String gender) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
