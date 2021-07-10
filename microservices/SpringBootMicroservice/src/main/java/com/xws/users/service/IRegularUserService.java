package com.xws.users.service;

import java.util.Collection;

import com.xws.users.users.model.roles.Agent;
import com.xws.users.users.model.roles.RegularUser;

public interface IRegularUserService {
    RegularUser findByUsername(String username);
    RegularUser deactivateUser(String username);
    Collection<RegularUser> searchByUsername(String username);

}
