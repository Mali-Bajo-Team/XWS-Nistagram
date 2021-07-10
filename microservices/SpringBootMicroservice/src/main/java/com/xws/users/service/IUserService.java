package com.xws.users.service;

import com.xws.users.dto.UserTokenState;
import com.xws.users.users.model.roles.UserAccount;

import java.util.List;

public interface IUserService {
	UserAccount findByEmail(String email );

	List<UserAccount> findAll();

	String getUserRole(UserAccount userAccount);

	UserAccount changePassword(String oldPassword, String newPassword);
	
	UserTokenState logIn(String username, String password);
}
