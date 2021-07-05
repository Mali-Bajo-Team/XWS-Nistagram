package com.xws.users.service;

import com.xws.users.dto.RegistrationRequestDTO;
import com.xws.users.users.model.RegistrationRequest;


public interface IRegistrationRequestService {
    RegistrationRequest createRegistrationRequest(RegistrationRequestDTO registrationRequestDTO);
}
