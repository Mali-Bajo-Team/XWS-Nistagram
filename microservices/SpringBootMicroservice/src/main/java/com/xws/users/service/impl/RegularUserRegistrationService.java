package com.xws.users.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xws.users.dto.UserRegisterDTO;
import com.xws.users.dto.adds.AddConsumer;
import com.xws.users.repository.IUserRepository;
import com.xws.users.service.IAuthorityService;
import com.xws.users.service.IRegularUserRegistrationService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.PrivacySettings;
import com.xws.users.users.model.enums.UserAccountStatus;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.users.model.roles.UserAccount;
import com.xws.users.util.security.exceptions.USConflictException;

@Service
public class RegularUserRegistrationService implements IRegularUserRegistrationService {

	private String addService = "http://add-service:8081/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRepository regularUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IAuthorityService authService;

	@Override
	@Transactional
	public UserAccount registerRegularUser(UserRegisterDTO user) {
		if (userRepository.existsByEmail(user.getEmail()))
			throw new USConflictException("The email is already taken.");
		if (userRepository.existsByUsername(user.getUsername()))
			throw new USConflictException("The username is already taken.");
		RegularUser addedRegularUser = addNewRegularUser(user);
		
		String url = addService + "internal/consumer";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<AddConsumer> entity = new HttpEntity<AddConsumer>(new AddConsumer(addedRegularUser), headers);
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}
		
		return addedRegularUser;
	}

	private RegularUser addNewRegularUser(UserRegisterDTO userRequest) {
		RegularUser newRegularUser = new RegularUser();
		newRegularUser.setName(userRequest.getName());
		newRegularUser.setSurname(userRequest.getSurname());
		newRegularUser.setEmail(userRequest.getEmail());
		newRegularUser.setUsername(userRequest.getUsername());
		newRegularUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		newRegularUser.setGender(userRequest.getGender());
		newRegularUser.setStatus(UserAccountStatus.ACTIVE);
		List<Authority> auth = authService.findByName("ROLE_REGULAR");
		newRegularUser.setAuthorities(auth);
		addPrivacySettings(newRegularUser);
		RegularUser addedPatient = regularUserRepository.save(newRegularUser);
		return addedPatient;
	}

	private void addPrivacySettings(RegularUser newRegularUser) {
		PrivacySettings privacySettings = new PrivacySettings();
		privacySettings.setPrivate(false);
		privacySettings.setAllowMessagesFromNotFollowed(true);
		privacySettings.setAllowTags(true);
		newRegularUser.setPrivacySettings(privacySettings);
	}
}
