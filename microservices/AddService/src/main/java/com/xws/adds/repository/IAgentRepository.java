package com.xws.adds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.adds.model.Agent;

public interface IAgentRepository extends JpaRepository<Agent, Long> {

	Agent findByUsername(String username);
	
}
