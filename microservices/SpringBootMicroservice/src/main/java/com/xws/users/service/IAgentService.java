package com.xws.users.service;

import com.xws.users.dto.AgentRegisterDTO;
import com.xws.users.users.model.roles.Agent;

public interface IAgentService {
    Agent registerAgent(AgentRegisterDTO agent);
}
