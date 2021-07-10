package com.xws.adds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.adds.model.Agent;
import com.xws.adds.model.CollaborationRequest;
import com.xws.adds.model.Influencer;

public interface ICollaborationRequestRepository extends JpaRepository<CollaborationRequest, Long>{

	CollaborationRequest findByRequesterAndInfluencer(Agent requester, Influencer influencer);
	
}
