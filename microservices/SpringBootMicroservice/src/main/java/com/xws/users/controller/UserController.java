package com.xws.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.dto.UserAccDTO;
import com.xws.users.service.impl.UserService;
import com.xws.users.users.model.roles.UserAccount;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	@Autowired
	private UserService userAccService;

	@PutMapping(consumes = "application/json")
	public ResponseEntity<UserAccDTO> updateUserAcc(@RequestBody UserAccount userAcc) {

		// a userAcc must exist
		UserAccount userAccForUpdate = userAccService.findOne(userAcc.getId());

		if (userAccForUpdate == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		userAccForUpdate.setEmail(userAcc.getEmail());
		userAccForUpdate.setPassword(userAcc.getPassword());

		userAcc = userAccService.save(userAccForUpdate);
		return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
	}

}
