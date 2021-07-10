package com.xws.users.repository;

import com.xws.users.users.model.Authority;
import com.xws.users.users.model.roles.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegularUserRepository extends JpaRepository<RegularUser, Long> {
    RegularUser findByUsername(String username);
    Boolean existsByUsername(String username);
    RegularUser findByEmail(String email);
    Boolean existsByEmail(String email);
}
