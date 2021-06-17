package com.xws.users.service.impl;

import java.util.Collection;
import java.util.List;

import com.xws.users.dto.RegularUserImageUpdateDTO;
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

	public RegularUser save(RegularUser regularUser) {
		return regularUserRepository.save(regularUser);
	}

	@Override
	public Collection<RegularUser> searchByUsername(String username) {
		List<RegularUser> allUsers = regularUserRepository.findAll();

		return CollectionUtil.findAll(allUsers, user -> user.getUsername().toLowerCase().contains(username));
	}


}
