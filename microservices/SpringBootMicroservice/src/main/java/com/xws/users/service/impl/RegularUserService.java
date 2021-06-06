package com.xws.users.service.impl;

import com.xws.users.repository.IRegularUserRepository;
import com.xws.users.service.IRegularUserService;
import com.xws.users.users.model.roles.RegularUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularUserService implements IRegularUserService {
    @Autowired
    private IRegularUserRepository regularUserRepository;

    @Override
    public RegularUser findByUsername(String username) {
        return regularUserRepository.findByUsername(username);
    }
}
