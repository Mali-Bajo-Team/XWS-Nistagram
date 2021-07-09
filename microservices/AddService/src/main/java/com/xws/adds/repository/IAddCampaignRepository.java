package com.xws.adds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.adds.model.AddCampaign;
import com.xws.adds.model.Agent;

public interface IAddCampaignRepository extends JpaRepository<AddCampaign, Long> {

	List<AddCampaign> findByAgent(Agent agent);
	
}
