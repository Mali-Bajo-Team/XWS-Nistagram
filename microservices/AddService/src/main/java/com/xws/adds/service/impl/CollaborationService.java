package com.xws.adds.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xws.adds.model.Agent;
import com.xws.adds.model.Collaboration;
import com.xws.adds.model.CollaborationRequest;
import com.xws.adds.model.Influencer;
import com.xws.adds.repository.IAgentRepository;
import com.xws.adds.repository.ICollaborationRepository;
import com.xws.adds.repository.ICollaborationRequestRepository;
import com.xws.adds.repository.IInfluencerRepository;
import com.xws.adds.service.ICollaborationService;
import com.xws.adds.util.exceptions.USAuthenticationException;
import com.xws.adds.util.exceptions.USAuthorizationException;
import com.xws.adds.util.exceptions.USConflictException;

@Service
public class CollaborationService implements ICollaborationService {

	@Autowired
	private IAgentRepository agentRepository;

	@Autowired
	private IInfluencerRepository influencerRepository;

	@Autowired
	private ICollaborationRepository collaborationRepository;

	@Autowired
	private ICollaborationRequestRepository requestRepository;

	@Override
	public List<String> getCollaboratingInfluencers(String agentUsername) {
		List<String> influencers = new ArrayList<String>();

		Agent agent = agentRepository.findByUsername(agentUsername);
		if (agent == null)
			throw new USAuthenticationException();

		for (Collaboration collaboration : agent.getInfluencers()) {
			influencers.add(collaboration.getInfluencer().getUsername());
		}

		return influencers;
	}

	@Override
	public List<String> getAvailableInfluencers(String agentUsername) {
		List<String> influencers = new ArrayList<String>();

		Agent agent = agentRepository.findByUsername(agentUsername);
		if (agent == null)
			throw new USAuthenticationException();

		for (Influencer influencer : influencerRepository.findAll()) {
			Collaboration collaboration = collaborationRepository.findByAgentAndInfluencer(agent, influencer);
			if (collaboration != null)
				continue;
			CollaborationRequest request = requestRepository.findByRequesterAndInfluencer(agent, influencer);
			if (request != null)
				continue;
			
			influencers.add(influencer.getUsername());
		}

		return influencers;
	}
	
	@Override
	public void requestCollaboration(String agentUsername, String influencerUsername) {
		Agent agent = agentRepository.findByUsername(agentUsername);
		if (agent == null)
			throw new USAuthenticationException();
		
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USConflictException();
		
		Collaboration collaboration = collaborationRepository.findByAgentAndInfluencer(agent, influencer);
		if (collaboration != null)
			throw new USConflictException();
		CollaborationRequest request = requestRepository.findByRequesterAndInfluencer(agent, influencer);
		if (request != null)
			return;
		
		request = new CollaborationRequest();
		request.setInfluencer(influencer);
		request.setRequester(agent);
		requestRepository.save(request);
	}
	
	@Override
	public List<CollaborationRequest> getCollaborationRequests(String influencerUsername) {
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USAuthenticationException();
		
		return influencer.getCollaborationRequests();
	}
	
	@Override
	@Transactional
	public void acceptCollaborationRequest(String influencerUsername, Long requestId) {
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USAuthenticationException();
		
		CollaborationRequest request = requestRepository.findById(requestId).orElse(null);
		if (request == null)
			throw new USConflictException();
		
		if (!request.getInfluencer().getUsername().equals(influencerUsername))
			throw new USAuthorizationException();
		
		Collaboration collaboration = new Collaboration();
		collaboration.setAgent(request.getRequester());
		collaboration.setInfluencer(influencer);
		
		collaborationRepository.save(collaboration);
		requestRepository.delete(request);
	}
	
	@Override
	@Transactional
	public void rejectCollaborationRequest(String influencerUsername, Long requestId) {
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USAuthenticationException();
		
		CollaborationRequest request = requestRepository.findById(requestId).orElse(null);
		if (request == null)
			throw new USConflictException();
		
		if (!request.getInfluencer().getUsername().equals(influencerUsername))
			throw new USAuthorizationException();
		
		requestRepository.delete(request);
	}

}
