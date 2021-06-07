package com.xws.users.controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.dto.RegularUserUpdateDTO;
import com.xws.users.dto.UserProfileDTO;
import com.xws.users.service.impl.RegularUserService;
import com.xws.users.users.model.roles.RegularUser;

@RestController
@RequestMapping(value = "profile")
public class ProfileController {

	@Autowired
	private RegularUserService regularUserService;

	@PreAuthorize("hasRole('REGULAR')")
	@GetMapping
	public ResponseEntity<RegularUserUpdateDTO> addUser(@RequestHeader(value = "user-username") String username) {
		RegularUser regularUser = regularUserService.findByUsername(username);

		if (regularUser == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new RegularUserUpdateDTO(regularUser));
	}

	@PreAuthorize("hasRole('REGULAR')")
	@PutMapping(consumes = "application/json")
	public ResponseEntity<RegularUserUpdateDTO> updateUserAcc(@RequestBody RegularUser regularUser) {

		RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUser.getUsername());

		if (regularUserForUpdate == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		regularUserForUpdate.setName(regularUser.getName());
		regularUserForUpdate.setSurname(regularUser.getSurname());
		regularUserForUpdate.setUsername(regularUser.getUsername());
		regularUserForUpdate.setEmail(regularUser.getEmail());
		regularUserForUpdate.setPhoneNumber(regularUser.getPhoneNumber());
		regularUserForUpdate.setDateOfBirth(regularUser.getDateOfBirth());
		regularUserForUpdate.setGender(regularUser.getGender());
		regularUserForUpdate.setLinkToWebSite(regularUser.getLinkToWebSite());
		regularUserForUpdate.setBio(regularUser.getBio());

		regularUser = regularUserService.save(regularUserForUpdate);
		return new ResponseEntity<>(new RegularUserUpdateDTO(regularUser), HttpStatus.OK);
	}

	@GetMapping("{username}")
	@PermitAll
	public ResponseEntity<UserProfileDTO> findProfile(@PathVariable(required = true) String username) {
		RegularUser regularUser = regularUserService.findByUsername(username);

		if (regularUser == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new UserProfileDTO(regularUser));
	}
}
