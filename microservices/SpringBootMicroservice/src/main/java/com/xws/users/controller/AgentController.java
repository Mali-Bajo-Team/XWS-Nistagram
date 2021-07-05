package com.xws.users.controller;


import com.xws.users.dto.RegistrationRequestDTO;
import com.xws.users.service.IRegistrationRequestService;
import com.xws.users.users.model.roles.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

public class AgentController {

    @Autowired
    private IRegistrationRequestService registrationRequestService;

    public ResponseEntity<RegistrationRequestDTO> createRegistrationRequest(@RequestBody RegistrationRequestDTO verificationRequestDTO, Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();

        return null;

    }
}
