package com.xws.users.dto;

import javax.validation.constraints.NotEmpty;

// DTO for login
public class JwtAuthenticationRequest {

	@NotEmpty(message = "Email is required.")
    private String email;
	@NotEmpty(message = "Password is required.")
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}