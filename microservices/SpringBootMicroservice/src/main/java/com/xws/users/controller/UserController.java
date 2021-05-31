package com.xws.users.controller;

import java.util.ArrayList;
import java.util.List;

import com.xws.users.dto.UserAccDTO;
import com.xws.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.users.model.UserAccount;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	@Autowired
	private UserService userAccService;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<UserAccDTO>> getUsersAccs() {

		List<UserAccount> usersACC = userAccService.findAll();

		// convert users to DTOs
		List<UserAccDTO> usersAccDTO = new ArrayList<>();
		for (UserAccount u : usersACC) {
			usersAccDTO.add(new UserAccDTO(u));
		}

		return new ResponseEntity<>(usersAccDTO, HttpStatus.OK);
	}

	@GetMapping("userAcc/{id}")
	public ResponseEntity<UserAccDTO> getUserAcc(@PathVariable Long id) {

		UserAccount userAcc = userAccService.findOne(id);

		if (userAcc == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserAccDTO> saveUserAcc(@RequestBody UserAccount userAcc) {

		UserAccount userAccForSave = new UserAccount();
		userAccForSave.setEmail(userAcc.getEmail());
		userAccForSave.setPassword(userAcc.getPassword());
		userAccForSave.setActive(true);

		userAcc = userAccService.save(userAccForSave);
		return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.CREATED);
	}

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

	@DeleteMapping(value = "userAcc/{id}")
	public ResponseEntity<Void> deleteUserAcc(@PathVariable Long id) {

		UserAccount userAcc = userAccService.findOne(id);

		if (userAcc != null) {
			userAccService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
