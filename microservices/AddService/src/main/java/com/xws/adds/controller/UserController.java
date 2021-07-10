package com.xws.adds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xws.adds.dto.UserUpdateDTO;
import com.xws.adds.model.AddConsumer;
import com.xws.adds.model.Agent;
import com.xws.adds.model.Influencer;
import com.xws.adds.repository.IAddConsumerRepository;
import com.xws.adds.repository.IAgentRepository;
import com.xws.adds.repository.IInfluencerRepository;

@RestController
public class UserController {

	@Autowired
	private IAgentRepository agentRepository;
	
	@Autowired
	private IInfluencerRepository influencerRepository;
	
	@Autowired
	private IAddConsumerRepository addConsumerRepository;
	
	@PostMapping("/internal/agent")
	public ResponseEntity<Void> saveAgent(@RequestBody Agent agent) {
		agentRepository.save(agent);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/internal/influencer")
	public ResponseEntity<Void> saveInfluencer(@RequestBody Influencer influencer) {
		influencerRepository.save(influencer);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/internal/consumer")
	public ResponseEntity<Void> saveConsumer(@RequestBody AddConsumer addConsumer) {
		addConsumerRepository.save(addConsumer);
		return ResponseEntity.noContent().build();
	}	
	
	@PostMapping("/internal/update")
	public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDTO update) {
		Agent agent = agentRepository.findByUsername(update.getOldUsername());
		Influencer influencer = influencerRepository.findByUsername(update.getOldUsername());
		AddConsumer consumer = addConsumerRepository.findByUsername(update.getOldUsername());
		
		if (agent != null) {
			agent.setUsername(update.getNewUsername());
			agent.setWebSite(update.getWebSiteLink());
			agentRepository.save(agent);
		}
		
		if (influencer != null) {
			influencer.setUsername(update.getNewUsername());
			influencerRepository.save(influencer);
		}
		
		if (consumer != null) {
			consumer.setUsername(update.getNewUsername());
			consumer.setDateOfBirth(update.getDateOfBirth());
			consumer.setGender(update.getGender());
			consumer.setUserCategory(update.getUserCategory());
			addConsumerRepository.save(consumer);
		}
		
		return ResponseEntity.noContent().build();
	}
}
