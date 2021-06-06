package com.xws.users.service;

import com.xws.users.users.model.roles.RegularUser;

public interface IRegularUserService {
    RegularUser findByUsername(String username);
}
