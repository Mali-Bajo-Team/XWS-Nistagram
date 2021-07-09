package com.xws.users.service;

import com.xws.users.dto.AgentRegisterDTO;
import com.xws.users.users.model.roles.Agent;

import java.util.List;

public interface IAgentService {
    Agent registerAgent(AgentRegisterDTO agent);
    List<Agent> findAllAgentRequestRegistration();
}
