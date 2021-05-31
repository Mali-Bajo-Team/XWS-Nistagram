package com.xws.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.users.users.model.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, Long>{
}
