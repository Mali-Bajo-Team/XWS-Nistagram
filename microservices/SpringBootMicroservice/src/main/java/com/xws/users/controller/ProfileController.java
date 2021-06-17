package com.xws.users.controller;


import com.xws.users.dto.RegularUserImageUpdateDTO;
import com.xws.users.users.model.PrivacySettings;
import com.xws.users.users.model.roles.UserAccount;
import com.xws.users.util.security.exceptions.USConflictException;
import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<RegularUserUpdateDTO> updateUserAcc(@RequestBody RegularUserUpdateDTO regularUserUpdateDTO) {

		RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUserUpdateDTO.getUsername());

		RegularUser regularUserForNewUsername = regularUserService.findByUsername(regularUserUpdateDTO.getNewusername());
		if (regularUserForUpdate == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (regularUserForNewUsername != null && !isUsernameChanged(regularUserUpdateDTO)) {
			throw new USConflictException("The username is already taken.");
		}

		regularUserForUpdate.setName(regularUserUpdateDTO.getName());
		regularUserForUpdate.setSurname(regularUserUpdateDTO.getSurname());
		regularUserForUpdate.setUsername(regularUserUpdateDTO.getNewusername());
		regularUserForUpdate.setEmail(regularUserUpdateDTO.getEmail());
		regularUserForUpdate.setPhoneNumber(regularUserUpdateDTO.getPhonenumber());
		regularUserForUpdate.setDateOfBirth(regularUserUpdateDTO.getBirthdaydate());
		regularUserForUpdate.setGender(regularUserUpdateDTO.getGender());
		regularUserForUpdate.setLinkToWebSite(regularUserUpdateDTO.getWebsite());
		regularUserForUpdate.setBio(regularUserUpdateDTO.getBio());

		RegularUser regularUserForUpdated = regularUserService.save(regularUserForUpdate);
		return new ResponseEntity<>(new RegularUserUpdateDTO(regularUserForUpdated), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('REGULAR')")
	@PostMapping("imageurlupdate")
	public ResponseEntity<RegularUserImageUpdateDTO> setProfileImageURL(@RequestBody RegularUserImageUpdateDTO regularUserImageUpdateDTO) {

		RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUserImageUpdateDTO.getUsername());

		if (regularUserForUpdate == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		regularUserForUpdate.setProfileImagePath(regularUserImageUpdateDTO.getProfileimagepath());

		regularUserService.save(regularUserForUpdate);
		RegularUserImageUpdateDTO regularUserImageUpdate = new RegularUserImageUpdateDTO(regularUserForUpdate.getProfileImagePath(), regularUserForUpdate.getUsername());
		return new ResponseEntity<>(regularUserImageUpdate, HttpStatus.OK);
	}

	private boolean isUsernameChanged(RegularUserUpdateDTO regularUserUpdateDTO) {
		return regularUserUpdateDTO.getUsername().equals(regularUserUpdateDTO.getNewusername());
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

	@PostMapping("setprivate/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Boolean> setprivate(@PathVariable(required = true) String username, Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isPrivate;

		if (regularUser == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setPrivate(true);
		isPrivate = true;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isPrivate, HttpStatus.OK);
	}

	@PostMapping("setpublic/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Boolean> setpublic(@PathVariable(required = true) String username, Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isPrivate;

		if (regularUser == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setPrivate(false);
		isPrivate = false;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isPrivate, HttpStatus.OK);
	}
}
