package com.xws.users.service.impl;
import com.xws.users.dto.UserRegisterDTO;
import com.xws.users.repository.IUserRepository;
import com.xws.users.service.IAuthorityService;
import com.xws.users.service.IRegularUserRegistrationService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.enums.ProfileStatus;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.users.model.roles.UserAccount;
import com.xws.users.util.security.exceptions.USConflictException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegularUserRegistrationService implements IRegularUserRegistrationService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRepository regularUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IAuthorityService authService;

	@Override
	public UserAccount registerRegularUser(UserRegisterDTO user) {
		if (userRepository.existsByEmail(user.getEmail()))
			throw new USConflictException("The email is already taken.");
		if (userRepository.existsByUsername(user.getUsername()))
			throw new USConflictException("The username is already taken.");
		RegularUser addedRegularUser = addNewRegularUser(user);
		return addedRegularUser;
	}

	private RegularUser addNewRegularUser(UserRegisterDTO userRequest) {
		RegularUser newRegularUser = new RegularUser();
		newRegularUser.setName(userRequest.getName());
		newRegularUser.setSurname(userRequest.getSurname());
		newRegularUser.setEmail(userRequest.getEmail());
		newRegularUser.setUsername(userRequest.getUsername());
		newRegularUser.setPassword( passwordEncoder.encode(userRequest.getPassword()));
		newRegularUser.setGender(userRequest.getGender());
		newRegularUser.setProfileStatus(ProfileStatus.ACTIVE);
		List<Authority> auth = authService.findByName("ROLE_REGULAR_USER");
        newRegularUser.setAuthorities(auth);
		RegularUser addedPatient = regularUserRepository.save(newRegularUser);
		return addedPatient;
	}
}
