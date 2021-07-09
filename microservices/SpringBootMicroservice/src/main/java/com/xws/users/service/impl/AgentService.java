package com.xws.users.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xws.users.dto.AgentRegisterDTO;
import com.xws.users.dto.adds.AddConsumer;
import com.xws.users.dto.adds.AddServiceAgent;
import com.xws.users.repository.IAgentRepository;
import com.xws.users.service.IAgentService;
import com.xws.users.service.IAuthorityService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.enums.UserAccountStatus;
import com.xws.users.users.model.roles.Agent;
import com.xws.users.util.security.exceptions.USConflictException;

@Service
public class AgentService implements IAgentService {

	private String addService = "http://add-service/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IAgentRepository agentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IAuthorityService authService;

	@Override
	public Agent registerAgent(AgentRegisterDTO agent) {
		if (agentRepository.existsByEmail(agent.getEmail()))
			throw new USConflictException("The email is already taken.");
		if (agentRepository.existsByUsername(agent.getUsername()))
			throw new USConflictException("The username is already taken.");
		Agent addedAgent = addNewAgent(agent);

		return addedAgent;
	}

	@Override
	@Transactional
	public Agent acceptRequest(String username) {
		Agent agent = agentRepository.findByUsername(username);
		agent.setStatus(UserAccountStatus.ACTIVE);
		Agent acceptedAgent = agentRepository.save(agent);

		String url = addService + "internal/consumer";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<AddConsumer> entity = new HttpEntity<AddConsumer>(new AddConsumer(acceptedAgent), headers);
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}
		
		String url2 = addService + "internal/agent";
		HttpHeaders headers2 = new HttpHeaders();
		HttpEntity<AddServiceAgent> entity2 = new HttpEntity<AddServiceAgent>(new AddServiceAgent(acceptedAgent), headers2);
		ResponseEntity<Void> responseEntity2 = restTemplate.exchange(url2, HttpMethod.POST, entity2, Void.class);
		if (!responseEntity2.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}

		return acceptedAgent;
	}

	@Override
	public List<Agent> findAllAgentRequestRegistration() {
		return agentRepository.findByStatus(UserAccountStatus.UNCOFIRMED);
	}

	private Agent addNewAgent(AgentRegisterDTO userRequest) {
		Agent newAgent = new Agent();
		newAgent.setName(userRequest.getName());
		newAgent.setSurname(userRequest.getSurname());
		newAgent.setEmail(userRequest.getEmail());
		newAgent.setUsername(userRequest.getUsername());
		newAgent.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		newAgent.setGender(userRequest.getGender());
		newAgent.setLinkToWebSite(userRequest.getLinkToWebSite());
//        newAgent.setStatus(UserAccountStatus.ACTIVE);
		newAgent.setStatus(UserAccountStatus.UNCOFIRMED);
		List<Authority> auth = authService.findByName("ROLE_AGENT");
		newAgent.setAuthorities(auth);
		Agent addedAgent = agentRepository.save(newAgent);
		return addedAgent;
	}

}
