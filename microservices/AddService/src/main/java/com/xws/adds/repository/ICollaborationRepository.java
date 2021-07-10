package com.xws.adds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.adds.model.Agent;
import com.xws.adds.model.Collaboration;
import com.xws.adds.model.Influencer;

public interface ICollaborationRepository extends JpaRepository<Collaboration, Long> {

	Collaboration findByAgentAndInfluencer(Agent agent, Influencer influencer);
	
}
