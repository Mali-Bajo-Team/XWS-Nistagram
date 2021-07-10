package com.xws.users.controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.xws.users.dto.RegularUserImageUpdateDTO;
import com.xws.users.dto.RegularUserUpdateDTO;
import com.xws.users.dto.UserProfileDTO;
import com.xws.users.dto.adds.AddConsumer;
import com.xws.users.dto.adds.UserUpdateDTO;
import com.xws.users.service.IAgentService;
import com.xws.users.service.impl.RegularUserService;
import com.xws.users.users.model.PrivacySettings;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.util.security.exceptions.USConflictException;

@RestController
@RequestMapping(value = "profile")
public class ProfileController {
	
	private String addService = "http://add-service:8081/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RegularUserService regularUserService;

	@Autowired
	private IAgentService agentService;

	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	@GetMapping
	public ResponseEntity<RegularUserUpdateDTO> addUser(@RequestHeader(value = "user-username") String username) {
		RegularUser regularUser = regularUserService.findByUsername(username);

		if (regularUser == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new RegularUserUpdateDTO(regularUser));
	}

	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	@PutMapping(consumes = "application/json")
	public ResponseEntity<RegularUserUpdateDTO> updateUserAcc(@RequestBody RegularUserUpdateDTO regularUserUpdateDTO) {

		RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUserUpdateDTO.getUsername());

		RegularUser regularUserForNewUsername = regularUserService
				.findByUsername(regularUserUpdateDTO.getNewusername());
		if (regularUserForUpdate == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (regularUserForNewUsername != null && !isUsernameChanged(regularUserUpdateDTO)) {
			throw new USConflictException("The username is already taken.");
		}

		String oldUsername = regularUserForUpdate.getUsername();

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
		

		UserUpdateDTO dto = new UserUpdateDTO(oldUsername, regularUserForUpdated);
		
		String url = addService + "internal/update";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<UserUpdateDTO> entity = new HttpEntity<UserUpdateDTO>(dto, headers);
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}
		
		return new ResponseEntity<>(new RegularUserUpdateDTO(regularUserForUpdated), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	@PostMapping("imageurlupdate")
	public ResponseEntity<RegularUserImageUpdateDTO> setProfileImageURL(
			@RequestBody RegularUserImageUpdateDTO regularUserImageUpdateDTO) {

		RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUserImageUpdateDTO.getUsername());

		if (regularUserForUpdate == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		regularUserForUpdate.setProfileImagePath(regularUserImageUpdateDTO.getProfileimagepath());

		regularUserService.save(regularUserForUpdate);
		RegularUserImageUpdateDTO regularUserImageUpdate = new RegularUserImageUpdateDTO(
				regularUserForUpdate.getProfileImagePath(), regularUserForUpdate.getUsername());
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
	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	public ResponseEntity<Boolean> setprivate(@PathVariable(required = true) String username,
			Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isPrivate;

		if (regularUser == null) {
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
	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	public ResponseEntity<Boolean> setpublic(@PathVariable(required = true) String username,
			Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isPrivate;

		if (regularUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setPrivate(false);
		isPrivate = false;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isPrivate, HttpStatus.OK);
	}

	@PostMapping("allowmessages/{username}")
	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	public ResponseEntity<Boolean> allowMessagesFromNotFollowed(@PathVariable(required = true) String username,
			Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isAllowed;

		if (regularUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setAllowMessagesFromNotFollowed(true);
		isAllowed = true;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isAllowed, HttpStatus.OK);
	}

	@PostMapping("notallowmessages/{username}")
	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	public ResponseEntity<Boolean> notAllowMessagesFromNotFollowed(@PathVariable(required = true) String username,
			Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isAllowed;

		if (regularUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setAllowMessagesFromNotFollowed(false);
		isAllowed = false;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isAllowed, HttpStatus.OK);
	}

	@PostMapping("allowtags/{username}")
	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	public ResponseEntity<Boolean> allowtags(@PathVariable(required = true) String username,
			Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isBanned;

		if (regularUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setAllowTags(true);
		isBanned = true;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isBanned, HttpStatus.OK);
	}

	@PostMapping("bantags/{username}")
	@PreAuthorize("hasAnyRole('REGULAR', 'AGENT', 'INFLUENCER')")
	public ResponseEntity<Boolean> bantags(@PathVariable(required = true) String username,
			Authentication authentication) {
		RegularUser regularUser = regularUserService.findByUsername(username);
		Boolean isBanned;

		if (regularUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PrivacySettings privacySettings = regularUser.getPrivacySettings();
		privacySettings.setAllowTags(false);
		isBanned = false;
		regularUser.setPrivacySettings(privacySettings);

		regularUserService.save(regularUser);

		return new ResponseEntity<>(isBanned, HttpStatus.OK);
	}

}
