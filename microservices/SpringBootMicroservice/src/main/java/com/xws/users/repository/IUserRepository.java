package com.xws.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xws.users.users.model.roles.UserAccount;

public interface IUserRepository extends JpaRepository<UserAccount, Long>{

    UserAccount findByUsername(String username);
    Boolean existsByUsername(String username);
    UserAccount findByEmail(String email);
    Boolean existsByEmail(String email);

}
