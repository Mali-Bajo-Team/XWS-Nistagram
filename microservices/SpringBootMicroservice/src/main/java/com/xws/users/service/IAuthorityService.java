package com.xws.users.service;
import com.xws.users.users.model.Authority;

import java.util.List;

public interface IAuthorityService {

    List<Authority> findById(Long id);

    List<Authority> findByName(String name);
}
