package com.xws.users.dto;

import javax.validation.constraints.NotEmpty;

// DTO for login
public class JwtAuthenticationRequest {

	@NotEmpty(message = "Username is required.")
	private String username;
	@NotEmpty(message = "Password is required.")
	private String password;

	public JwtAuthenticationRequest() {
		super();
	}

	public JwtAuthenticationRequest(@NotEmpty(message = "Username is required.") String username,
			@NotEmpty(message = "Password is required.") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}