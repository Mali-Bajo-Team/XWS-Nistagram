package com.xws.users.service.impl;

import com.xws.users.repository.IAuthorityRepository;
import com.xws.users.service.IAuthorityService;
import com.xws.users.users.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService implements IAuthorityService {

    @Autowired
    private IAuthorityRepository authorityRepository;

    @Override
    public List<Authority> findById(Long id) {
        Authority auth = this.authorityRepository.getOne(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByName(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

}
