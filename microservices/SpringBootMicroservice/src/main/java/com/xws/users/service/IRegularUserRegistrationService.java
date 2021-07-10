package com.xws.users.service;


import com.xws.users.dto.UserRegisterDTO;
import com.xws.users.users.model.roles.UserAccount;

public interface IRegularUserRegistrationService {

	UserAccount registerRegularUser(UserRegisterDTO user);

}
