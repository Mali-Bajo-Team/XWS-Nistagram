package com.xws.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.users.model.roles.UserAccount;

@RestController
public class AuthenticationCheckController {

	@GetMapping("/check-token")
	public ResponseEntity<Void> checkToken(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		return ResponseEntity.noContent().header("user-id", user.getId().toString()).header("user-role", user.getRole())
				.header("user-username", user.getUsername()).build();
	}

}
