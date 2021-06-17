package com.xws.users.controller;

import com.xws.users.dto.RegularUserImageUpdateDTO;
import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.service.IVerificationRequestService;
import com.xws.users.users.model.roles.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/verification")
public class VerificationRequestController {

    @Autowired
    private IVerificationRequestService verificationRequestService;

    @PreAuthorize("hasRole('REGULAR')")
    @PostMapping
    public ResponseEntity<VerificationRequestDTO> createVerificationRequest(@RequestBody VerificationRequestDTO verificationRequestDTO, Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        verificationRequestDTO.setRequesterUsername(user.getUsername());
        VerificationRequestDTO verificationRequestDTONew = new VerificationRequestDTO(verificationRequestService.createVerificationRequest(verificationRequestDTO));
        return new ResponseEntity<>(verificationRequestDTONew, HttpStatus.OK);
    }

}
