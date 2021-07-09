package com.xws.users.repository;

import com.xws.users.users.model.enums.UserAccountStatus;
import com.xws.users.users.model.roles.Agent;
import com.xws.users.users.model.roles.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAgentRepository extends JpaRepository<Agent, Long> {
    Agent findByUsername(String username);
    List<Agent> findByStatus(UserAccountStatus status);
    Boolean existsByUsername(String username);
    Agent findByEmail(String email);
    Boolean existsByEmail(String email);
}
