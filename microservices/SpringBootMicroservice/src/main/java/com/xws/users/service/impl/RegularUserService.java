package com.xws.users.service.impl;

import java.util.Collection;
import java.util.List;

import com.xws.users.dto.RegularUserImageUpdateDTO;
import com.xws.users.users.model.enums.UserAccountStatus;
import com.xws.users.users.model.roles.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xws.users.repository.IRegularUserRepository;
import com.xws.users.service.IRegularUserService;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.util.CollectionUtil;

@Service
public class RegularUserService implements IRegularUserService {
	@Autowired
	private IRegularUserRepository regularUserRepository;

	@Override
	public RegularUser findByUsername(String username) {
		return regularUserRepository.findByUsername(username);
	}

	@Override
	public RegularUser deactivateUser(String username) {
		RegularUser user = regularUserRepository.findByUsername(username);
		user.setStatus(UserAccountStatus.DEACTIVATED);
		RegularUser deactivatedUser = regularUserRepository.save(user);
		return deactivatedUser;
	}

	public RegularUser save(RegularUser regularUser) {
		return regularUserRepository.save(regularUser);
	}

	@Override
	public Collection<RegularUser> searchByUsername(String username) {
		List<RegularUser> allUsers = regularUserRepository.findAll();

		return CollectionUtil.findAll(allUsers, user -> user.getUsername().toLowerCase().contains(username));
	}


}
