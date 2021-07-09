package com.xws.users.service.impl;

import com.xws.users.dto.AgentRegisterDTO;
import com.xws.users.dto.UserRegisterDTO;
import com.xws.users.repository.IAgentRepository;
import com.xws.users.service.IAgentService;
import com.xws.users.service.IAuthorityService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.enums.UserAccountStatus;
import com.xws.users.users.model.roles.Agent;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.util.security.exceptions.USConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService implements IAgentService {

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

    private Agent addNewAgent(AgentRegisterDTO userRequest) {
        Agent newAgent = new Agent();
        newAgent.setName(userRequest.getName());
        newAgent.setSurname(userRequest.getSurname());
        newAgent.setEmail(userRequest.getEmail());
        newAgent.setUsername(userRequest.getUsername());
        newAgent.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newAgent.setGender(userRequest.getGender());
        newAgent.setLinkToWebSite(userRequest.getLinkToWebSite());
        newAgent.setStatus(UserAccountStatus.ACTIVE);
//        newAgent.setStatus(UserAccountStatus.UNCOFIRMED);
        List<Authority> auth = authService.findByName("ROLE_AGENT");
        newAgent.setAuthorities(auth);
        Agent addedAgent = agentRepository.save(newAgent);
        return addedAgent;
    }

}
