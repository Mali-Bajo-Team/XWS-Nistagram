package com.xws.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PreAuthorize("hasRole('REGULAR')")
	@GetMapping("/test")
	public ResponseEntity<String> getUsersAccs() {
		return ResponseEntity.ok("Test test!");
	}

}
